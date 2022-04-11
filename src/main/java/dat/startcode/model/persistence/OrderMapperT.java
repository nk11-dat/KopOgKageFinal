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
    public Order confirmOrder(int userId, int status, int totalSum, Date date) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        Order order;
        int newId = 0;
        String sql = "insert into user (user_id, status, total_sum, order_date) values (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setInt(1, userId);
                ps.setInt(1, status);
                ps.setInt(1, totalSum);
                ps.setDate(4, (java.sql.Date) date);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next()) {
                    newId = idResultset.getInt(1);
                    order = new Order(newId, userId, status, totalSum, date);
                } else
                {
                    throw new DatabaseException("Bestillingen kunne ikke oprettes.");
                }
            }
        } catch (SQLException | DatabaseException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
        return order;
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
