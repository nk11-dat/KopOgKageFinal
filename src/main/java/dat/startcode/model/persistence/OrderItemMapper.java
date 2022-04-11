package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderInformationDTO;
import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderItemMapper
{
    ConnectionPool connectionPool;

    public OrderItemMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    public OrderItemDTOT getOrderItemPrice(int toppingId, int bottomId, int quantity) throws DatabaseException
    {

        OrderItemDTOT orderItemDTOT = null;
        int cupcakePrice = -1;
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderItemDTOT> orderItemDTOList = new ArrayList<>();

        String sql = "SELECT  (" +
                "    SELECT price" +
                "    FROM   topping" +
                "    WHERE topping_id = ?" +
                ") AS topping_price," +
                "(" +
                "    SELECT flavor" +
                "    FROM   topping" +
                "    WHERE topping_id = ?" +
                ") AS topping_flavor," +
                "(" +
                "    SELECT price" +
                "    FROM   bottom" +
                "    WHERE bottom_id = ?" +
                ") AS bottom_price," +
                "(" +
                "    SELECT flavor" +
                "    FROM   bottom" +
                "    WHERE bottom_id = ?" +
                ") AS bottom_flavor;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, toppingId);
                ps.setInt(2, toppingId);
                ps.setInt(3, bottomId);
                ps.setInt(4, bottomId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int bottom = rs.getInt(3);
                    int topping = rs.getInt("topping_price");
                    String toppingFlavor = rs.getString("topping_flavor");
                    String bottomFlavor = rs.getString("bottom_flavor");


                    cupcakePrice = (topping + bottom) * quantity;
                    orderItemDTOT = new OrderItemDTOT(bottomFlavor, toppingFlavor, quantity, cupcakePrice);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }
        return orderItemDTOT;
    }


}
