package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapperT;
import dat.startcode.model.persistence.UserMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "ConfirmOrder", urlPatterns = "/ConfirmOrder")
public class ConfirmOrder extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override           //bekræfter order her
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        //session.setAttribute("order", null); // adding empty order object to session scope
        //OrderMapperT orderMapperT = new OrderMapperT(connectionPool);

        int totalSum = Integer.parseInt(request.getParameter("total_sum"));

        //int userId = Integer.parseInt(request.getParameter("user_id"));
        //int status = Integer.parseInt(request.getParameter("status"));
        //int totalSum = Integer.parseInt(request.getParameter("total_sum"));
        //Date date = new Date(request.getParameter("date"));

        // Order order = orderMapperT.confirmOrder(userId, status,totalSum,date);
        session.setAttribute("order", totalSum); // adding order object to session scope
        request.getRequestDispatcher("WEB-INF/thanks.jsp").forward(request, response);

    }
}

