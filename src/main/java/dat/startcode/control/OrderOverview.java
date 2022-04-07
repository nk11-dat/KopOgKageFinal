package dat.startcode.control;

import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.OrderMapper;
import dat.startcode.model.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "OrderOverview", urlPatterns = "/OrderOverview")
public class OrderOverview extends HttpServlet {
    private OrderMapper orderMapper;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        List<OrderOverviewHeaderAdminDTO> orderDTOList = null;

        try
        {
            orderDTOList = orderMapper.getAllOrders();
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("fejlbesked", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("orderDTOList", orderDTOList);
//        request.setAttribute("orderItemDTOList",);
        request.getRequestDispatcher("WEB-INF/adminIndex.jsp").forward(request, response);
    }

    @Override //Bruges til at opdatere order status fra adminIndex.jsp når der klikkes på [Button]
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        //TODO: Brug en Orde- entity istedet for bare at benytte orderId?
            //TODO: Hvad mener jon/nikolaj om det?
        int orderId = Integer.parseInt(request.getParameter("orderid"));  // hent brugerobjekt ud fra session scope

        try
        {
            orderMapper.setOrderStatusById(orderId);
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("fejlbesked", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        doGet(request, response);
//        request.getRequestDispatcher("adminIndex.jsp").forward(request, response);
    }
}
