package dat.startcode.control;

import dat.startcode.model.DTO.CupcakeDTO;
import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CupcakeOrder", urlPatterns = "/CupcakeOrder")
public class CupcakeOrder extends HttpServlet
{
    private CupcakeMapper cupcakeMapper;
    private OrderItemMapper orderItemMapper;
    private ToppingMapper toppingMapper;
    private BottomMapper bottomMapper;

    public void init() throws ServletException
    {

        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        cupcakeMapper = new CupcakeMapper(connectionPool);
        orderItemMapper = new OrderItemMapper(connectionPool);
        toppingMapper = new ToppingMapper(connectionPool);
        bottomMapper = new BottomMapper(connectionPool);

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
        HttpSession session = request.getSession();

        List<OrderItemDTOT> OrderItemList = (List<OrderItemDTOT>) session.getAttribute("OrderItemList");

        if (OrderItemList == null) {
            OrderItemList = new ArrayList<>();
        }



    int bottomValue = Integer.parseInt(request.getParameter("bottom"));
    int toppingValue = Integer.parseInt(request.getParameter("topping"));
    int quantityValue = Integer.parseInt(request.getParameter("quantity"));

        try {
            Topping tempTopping = toppingMapper.getToppingbyId(toppingValue);
            Bottom tempBottom = bottomMapper.getBottomById(bottomValue);

            int cupcakePrice = quantityValue*(tempTopping.getPrice() + tempBottom.getPrice());

            OrderItemDTOT temp = new OrderItemDTOT(tempBottom.getFlavor(),tempTopping.getFlavor(),quantityValue, cupcakePrice);

            OrderItemList.add(temp);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        session.setAttribute("OrderItemList", OrderItemList);
        request.getRequestDispatcher("WEB-INF/order.jsp").forward(request, response);
    }
}
