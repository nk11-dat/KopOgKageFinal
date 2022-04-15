package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderInformationDTO;
import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderitem;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderMapperT implements IOrderMapperT
{
    ConnectionPool connectionPool;

    public OrderMapperT(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<OrderItemDTOT> getOrderItemByOrderId(int orderId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderItemDTOT> orderItemDTOList = new ArrayList<>();

        String sql = "SELECT cupcake.orderitem.order_id, bottom.flavor as bottom, topping.flavor as topping, quantity, SUM(quantity*(topping.price + bottom.price)) as price " +
                "FROM orderitem " +
                "inner join bottom " +
                "using (bottom_id) " +
                "inner join topping " +
                "using (topping_id) " +
                "where order_id = ? " +
                "group by orderitem_id";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String bottom = rs.getString("bottom");
                    String topping = rs.getString("topping");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    orderItemDTOList.add(new OrderItemDTOT(bottom, topping, quantity, price));
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }
        return orderItemDTOList;
    }

    @Override
    public int getTotalSumByOrderId(int orderId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        int totalPrice = 0;

        String sql = "SELECT cupcake.orderitem.order_id, SUM(quantity*(topping.price + bottom.price)) as total_price " +
                "FROM orderitem " +
                "inner join bottom " +
                "using (bottom_id) " +
                "inner join topping " +
                "using (topping_id) " +
                "where order_id = ? " +
                "group by order_id;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    totalPrice = rs.getInt("total_price");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }
        return totalPrice;
    }

    @Override
    public Order confirmOrder(int userId, boolean status, int totalSum) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Order order = null;
        int newOrderId = 0;
        String sql = "insert into cupcake.order (user_id, status, total_sum, order_date) values (?, ?, ?, NOW())";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setInt(1, userId);
                ps.setBoolean(2, status);
                ps.setInt(3, totalSum);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next()) {
                    newOrderId = idResultset.getInt(1);
                    order = new Order(newOrderId, userId, status, totalSum);
                } else
                {
                    throw new DatabaseException("Bestillingen kunne ikke oprettes.");
                }
            }
        } catch (SQLException | DatabaseException ex)
        {
            throw new DatabaseException(ex, "Orderen kunne ikke sættes i databasen");
        }
        return order;
    }


    public Orderitem insertOrderItem(int orderId, int toppingId, int bottomId, int quantity) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Orderitem orderitem = null;
        int newOrderItemId = 0;
        String sql = "insert into cupcake.orderitem (order_id, topping_id, bottom_id, quantity) values (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setInt(1, orderId);
                ps.setInt(2, toppingId);
                ps.setInt(3, bottomId);
                ps.setInt(4, quantity);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next()) {
                    newOrderItemId = idResultset.getInt(1);
                    orderitem = new Orderitem(newOrderItemId, orderId, toppingId, bottomId, quantity);
                } else
                {
                    throw new DatabaseException("Orderitem kunne ikke oprettes.");
                }
            }
        } catch (SQLException | DatabaseException ex)
        {
            throw new DatabaseException(ex, "Orderitem kunne ikke sættes i databasen");
        }
        return orderitem;
    }

    @Override
    public OrderInformationDTO showOrderInformation(int orderId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        OrderInformationDTO orderInformationDTO = null;

        String sql = "SELECT order_id, cupcake.user.username, order_date " +
                "FROM cupcake.order " +
                "inner join cupcake.user " +
                "using (user_id) " +
                "where order_id = ? " +
                "group by order_id;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    String name = rs.getString("username");
                    Date date = rs.getDate("order_date");
                    orderInformationDTO = new OrderInformationDTO(orderId, name, date);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }

        return orderInformationDTO;
    }
}
