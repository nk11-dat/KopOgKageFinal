package dat.startcode.control;

import dat.startcode.model.DTO.OrderInformationDTO;
import dat.startcode.model.DTO.OrderItemDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderitem;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderItemMapper;
import dat.startcode.model.persistence.OrderMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "ConfirmOrder", urlPatterns = "/ConfirmOrder")
public class ConfirmOrder extends HttpServlet
{
    private ConnectionPool connectionPool;
    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
        orderItemMapper = new OrderItemMapper(connectionPool);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override           //bekræfter order her
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        List<OrderItemDTO> orderItemDTOList = null;
        Order order = null;
        Orderitem orderitem = null;
        OrderInformationDTO orderInformationDTO = null;
        int totalPrice = 0;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getUserId();

        // Insert order into database
        orderItemDTOList = (List<OrderItemDTO>) session.getAttribute("OrderItemList");
        for (OrderItemDTO orderItemDTOT : orderItemDTOList) {
            totalPrice += orderItemDTOT.getPrice();
        }

        try {
            order = orderMapper.confirmOrder(userId, false,totalPrice);
        } catch (DatabaseException e) {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        int orderId = order.getOrderId();
        int totalSum = order.getTotalSum();

        // Insert orderitem into database
        for (OrderItemDTO orderItemDTOT : orderItemDTOList) {
            int bottomId = 0;
            int toppingId = 0;
            int quantity = orderItemDTOT.getQuantity();

            try {
                bottomId = orderItemMapper.getBottomIdByFlavor(orderItemDTOT.getBottom());
                toppingId = orderItemMapper.getToppingIdByFlavor(orderItemDTOT.getTopping());
                orderitem = orderMapper.insertOrderItem(orderId, toppingId, bottomId, quantity);
            } catch (DatabaseException e) {
                Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
                request.setAttribute("errormessage", e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        }
        try {
            orderInformationDTO = orderMapper.showOrderInformation(orderId);
        } catch (DatabaseException e) {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        request.setAttribute("orderInformationDTO", orderInformationDTO);
        request.setAttribute("totalSum", totalSum);
        request.setAttribute("orderItemDTOList", orderItemDTOList);
        request.getRequestDispatcher("WEB-INF/receipt.jsp").forward(request, response);

        session.removeAttribute("OrderItemList");
    }
}

