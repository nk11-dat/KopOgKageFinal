package dat.startcode.control;

import dat.startcode.model.DTO.CupcakeDTO;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.CupcakeMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CupcakeOrder", urlPatterns = "/CupcakeOrder")
public class CupcakeOrder extends HttpServlet
{
    private CupcakeMapper cupcakeMapper;


    public void init() throws ServletException
    {

        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        cupcakeMapper = new CupcakeMapper(connectionPool);

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        response.setContentType("text/html");
        List<Topping> cupcakeToppingList = null;
        List<Bottom> cupcakeBottomList = null;

        try
        {
            cupcakeToppingList = cupcakeMapper.getToppings();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            cupcakeBottomList = cupcakeMapper.getBottoms();
        } catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("cupcakeToppingList", cupcakeToppingList);
        servletContext.setAttribute("cupcakeBottomList", cupcakeBottomList);

        //  request.setAttribute("cupcakeToppingList", cupcakeToppingList);
        // request.setAttribute("cupcakeBottomList", cupcakeBottomList);

        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    request.getParameterValues("topping");


    int toppingValue = Integer.parseInt(request.getParameter("bottom"));


        request.getRequestDispatcher("WEB-INF/orderOverviewT.jsp").forward(request, response);
    }
}
