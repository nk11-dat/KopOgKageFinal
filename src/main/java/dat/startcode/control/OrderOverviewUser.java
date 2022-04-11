package dat.startcode.control;

import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.OrderMapper;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapperT;

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

@WebServlet(name = "OrderOverviewUser", urlPatterns = "/OrderOverviewUser")
public class OrderOverviewUser extends HttpServlet {
    private OrderMapper orderMapper;
    private OrderMapperT orderMapperT;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapperT = new OrderMapperT(connectionPool);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        List<OrderItemDTOT> orderItemDTOList = null;
        int totalPrice = 0;

        HttpSession session = request.getSession();
        session.getAttribute("bla");
        // TODO: mangler request getAttribut id fra bestillings side
        orderItemDTOList = (List<OrderItemDTOT>) session.getAttribute("OrderItemList");
        for (OrderItemDTOT orderItemDTOT : orderItemDTOList) {
            totalPrice += orderItemDTOT.getPrice();
        }
        //  totalPrice = orderMapperT.getTotalSumByOrderId(1);
        request.setAttribute("orderItemDTOList", orderItemDTOList);
        request.setAttribute("totalPrice", totalPrice);
        request.getRequestDispatcher("WEB-INF/orderOverviewT.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
