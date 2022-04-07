package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderItemOverviewAdminDTO;
import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.entities.Order;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements IOrderMapper{
    private ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<OrderItemOverviewAdminDTO> getOrdersItems(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderItemOverviewAdminDTO> orderItemsList = new ArrayList<>();

        String sql = "SELECT cupcake.order.order_id, CONCAT(topping.flavor,' ', bottom.flavor) as cupcake, cupcake.orderitem.quantity " +
                "FROM cupcake.order " +
                "INNER JOIN orderitem " +
                "USING(order_id) " +
                "INNER JOIN topping " +
                "USING(topping_id) " +
                "INNER JOIN bottom " +
                "USING(bottom_id) " +
                "WHERE cupcake.order.order_id = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int quantity = rs.getInt("quantity");
                    String flavor = rs.getString("cupcake");
                    OrderItemOverviewAdminDTO newOrderItem = new OrderItemOverviewAdminDTO(quantity, flavor);
                    orderItemsList.add(newOrderItem);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }
        return orderItemsList;
    }

    @Override
    public List<OrderOverviewHeaderAdminDTO> getAllOrders() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderOverviewHeaderAdminDTO> orderList = new ArrayList<>();

        String sql = "SELECT DISTINCT cupcake.order.order_id, user.username, cupcake.order.total_sum, cupcake.order.order_date, cupcake.order.status " +
                "FROM user " +
                "INNER JOIN cupcake.order " +
                "USING(user_id) " +
                "INNER JOIN orderitem " +
                "USING(order_id);";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int orderId = rs.getInt("order_id");
                    String username = rs.getString("username");
                    int totalsum = rs.getInt("total_sum");
                    boolean status = rs.getBoolean("status");
                    Date date = rs.getDate("order_date");
                    List<OrderItemOverviewAdminDTO> orderItems = getOrdersItems(orderId);
                    OrderOverviewHeaderAdminDTO newOrderHeader = new OrderOverviewHeaderAdminDTO(orderId, username, totalsum, status, date, orderItems);
                    orderList.add(newOrderHeader);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af ordere fra databasen");
        }
        return orderList;
    }

    @Override
    public boolean setOrderStatusById(int orderid) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        String sql = "UPDATE cupcake.order SET status = 1 WHERE cupcake.order.order_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderid);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                } else {
                    throw new DatabaseException("Mere/mindre end 1 row affected da order med id = " + orderid + ". Skulle updates! (check evt. databasen for fejl)");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke opdatere order status for order med id = " + orderid);
        }

        return result;
    }

    @Override
    public Order getOrderById(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "orderId=" + orderId);
        return null;
    }
}
