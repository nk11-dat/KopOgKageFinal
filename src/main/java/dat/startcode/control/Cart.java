package dat.startcode.control;

import dat.startcode.model.DTO.OrderItemDTO;
import dat.startcode.model.config.ApplicationStart;
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

@WebServlet(name = "Cart", urlPatterns = "/Cart")
public class Cart extends HttpServlet {
    private OrderMapper orderMapper;

    @Override
    public void init() throws ServletException {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        List<OrderItemDTO> orderItemDTOList = null;
        int totalPrice = 0;

        HttpSession session = request.getSession();
        session.getAttribute("bla");
        orderItemDTOList = (List<OrderItemDTO>) session.getAttribute("OrderItemList");
        if (orderItemDTOList != null) {
            for (OrderItemDTO orderItemDTOT : orderItemDTOList) {
                totalPrice += orderItemDTOT.getPrice();
            }
        }
        request.setAttribute("orderItemDTOList", orderItemDTOList);
        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("WEB-INF/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
