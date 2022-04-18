package dat.startcode.control;

import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.DTO.UserIdTotalSumDTO;
import dat.startcode.model.DTO.UserOrdersDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.OrderMapper;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dat.startcode.model.services.Authentication.isRoleAllowed;

@WebServlet(name = "OrderOverviewAdmin", urlPatterns = "/OrderOverviewAdmin")
public class OrderOverviewAdmin extends HttpServlet {
    private OrderMapper orderMapper;
    private UserMapper userMapper;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
        userMapper = new UserMapper(connectionPool);
    }

    @Override //Indlæser OrderOverviewAdmin med data fra mySQL hvis man er logged ind som admin
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        List<OrderOverviewHeaderAdminDTO> orderDTOList = null;
        List<UserOrdersDTO> userOrdersDTOs = new ArrayList<>();
        List<User> userList = null;
        try
        {
            if (isRoleAllowed(2, request))
            {
                orderDTOList = orderMapper.getAllOrders();
                userList = userMapper.showAllUsers();
                for (User user : userList) {
                    List<OrderOverviewHeaderAdminDTO> tempOrderHeader = new ArrayList<>();
                    for (OrderOverviewHeaderAdminDTO orderHeader : orderDTOList) {
                        if (orderHeader.getCostumerUsername().equalsIgnoreCase(user.getUsername()))
                            tempOrderHeader.add(orderHeader);
                    }
                        userOrdersDTOs.add(new UserOrdersDTO(user, tempOrderHeader));
                }
            }
        }
        catch (DatabaseException | SQLException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("userOrdersDTOs", userOrdersDTOs); //ændre denne til UserOrdersDTO
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
            UserIdTotalSumDTO userIdAndTotalSum = orderMapper.getUserIdAndTotalSumByOrderId(orderId);
            int userId = userIdAndTotalSum.getUserId();
            int price = userIdAndTotalSum.getTotalSum();

            int balance = orderMapper.getUserBalanceByUserId(userId);
            if (balance >= price)
            {
                orderMapper.updateUserBalanceByUserId(userId, price);
                orderMapper.setOrderStatusById(orderId);
            }
            else {
                request.setAttribute("insufficient_funds", "Kunden har ikke nok penge til betale for orderen!");
            }
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
