package dat.startcode.control;

import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.OrderMapper;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "OrderOverviewAdmin", urlPatterns = "/OrderOverviewAdmin")
public class OrderOverviewAdmin extends HttpServlet {
    private OrderMapper orderMapper;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
    }

    @Override //Indlæser OrderOverviewAdmin med data fra mySQL hvis man er logged ind som admin
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        response.setContentType("text/html");
        List<OrderOverviewHeaderAdminDTO> orderDTOList = null;
        try
        {
            User user = (User) session.getAttribute("user");
            if (user != null && user.getRoleId() == 2)
                orderDTOList = orderMapper.getAllOrders();
            else
                throw new DatabaseException("Du skal være logget ind som admin for at tilgå denne side!");
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("orderDTOList", orderDTOList);
        request.getRequestDispatcher("WEB-INF/adminIndex.jsp").forward(request, response);
    }

    @Override //Bruges til at opdatere order status fra adminIndex.jsp når der klikkes på [Godkend]
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        //TODO: Brug en Orde- entity istedet for bare at benytte orderId?
            //TODO: Hvad mener jon/nikolaj om det?
        int orderId = Integer.parseInt(request.getParameter("orderid"));  //Hent brugerobjekt ud fra session scope

        try
        {
            boolean result = orderMapper.setOrderStatusById(orderId);
            if (!result)
                request.setAttribute("insufficient_funds", "Kunden har ikke nok penge til at kunne betale!");
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        doGet(request, response); //Indlæs siden igen
//        request.getRequestDispatcher("adminIndex.jsp").forward(request, response);
    }
}