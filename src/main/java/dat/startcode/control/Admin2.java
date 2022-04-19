package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Admin2", urlPatterns = "/Admin2")
public class Admin2 extends HttpServlet {
    private OrderMapper orderMapper;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        orderMapper = new OrderMapper(connectionPool);
    }

    @Override //Bruges til at slette order adminIndex.jsp når der klikkes på [Slet]
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //TODO: Ellers skal du hive koden ud i en selvstændig klasse i service pakken og så sende request og response derned
        int orderId = Integer.parseInt(request.getParameter("orderid"));  //Hent orderId ud button press
        boolean success = false;
        try
        {
            success = orderMapper.deleteOrderByOrderId(orderId);
            if (!success)
            {
                throw new DatabaseException("Noget gik galt da der skulle slettes fra databasen. Kontakt en voksen.");
            }
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }

        request.getRequestDispatcher("Admin").forward(request, response); //Indlæs siden igen
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //this does nothing
    }
}
