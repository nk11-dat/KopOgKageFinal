package dat.startcode.model.persistence;

import dat.startcode.model.DTO.*;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderitem;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements IOrderMapper {
    private ConnectionPool connectionPool;

    public OrderMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<OrderItemAdminDTO> getOrdersItems(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderItemAdminDTO> orderItemsList = new ArrayList<>();

        String sql = "SELECT cupcake.order.order_id, CONCAT(bottom.flavor, ' ', topping.flavor) as cupcake, cupcake.orderitem.quantity " +
                "FROM cupcake.order " +
                "INNER JOIN orderitem " +
                "USING(order_id) " +
                "INNER JOIN topping " +
                "USING(topping_id) " +
                "INNER JOIN bottom " +
                "USING(bottom_id) " +
                "WHERE cupcake.order.order_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    String flavor = rs.getString("cupcake");
                    OrderItemAdminDTO newOrderItem = new OrderItemAdminDTO(quantity, flavor);
                    orderItemsList.add(newOrderItem);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning af 'orderItems' fra databasen");
        }
        return orderItemsList;
    }

    @Override
    public List<OrderHeaderAdminDTO> getAllOrders() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderHeaderAdminDTO> orderList = new ArrayList<>();

        String sql = "SELECT DISTINCT cupcake.order.order_id, user.username, cupcake.order.total_sum, cupcake.order.order_date, cupcake.order.status " +
                "FROM user " +
                "INNER JOIN cupcake.order " +
                "USING(user_id) " +
                "INNER JOIN orderitem " +
                "USING(order_id);";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    String username = rs.getString("username");
                    int totalsum = rs.getInt("total_sum");
                    boolean status = rs.getBoolean("status");
                    Date date = rs.getDate("order_date");
                    List<OrderItemAdminDTO> orderItems = getOrdersItems(orderId);
                    OrderHeaderAdminDTO newOrderHeader = new OrderHeaderAdminDTO(orderId, username, totalsum, status, date, orderItems);
                    orderList.add(newOrderHeader);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl under indlæsning af ordere fra databasen");
        }
        return orderList;
    }

    @Override
    public boolean setOrderStatusById(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        String sql = "UPDATE cupcake.order SET status = 1 WHERE cupcake.order.order_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    result = true;
                } else {
                    throw new DatabaseException("Mere/mindre end 1 row affected da order med id = " + orderId + ". Skulle updates! (check evt. databasen for fejl)");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke opdatere order status for order med id = " + orderId);
        }

        return result;
    }

    public UserIdAndTotalSumDTO getUserIdAndTotalSumByOrderId(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        UserIdAndTotalSumDTO idAndSum = null;

        String sql = "SELECT user_id, total_sum FROM cupcake.order WHERE order_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int totalSum = rs.getInt("total_sum");

                    idAndSum = new UserIdAndTotalSumDTO(userId, totalSum);
                    if (idAndSum == null)
                        throw new DatabaseException("Fejl under indlæsning af user_id/total_sum fra databasen, fandt ingen order ud fra orderid: " + orderId);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl Kunne ikke oprette forbindelse til databasen i metode: getUserIdAndTotalSumByOrderId");
        }

        return idAndSum;
    }

    @Override
    public boolean deleteOrderByOrderId(int orderid) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        try (Connection connection = connectionPool.getConnection()) {

            //Først slettes de orderitems som er ophængt på orderen
            String sql = "DELETE FROM orderitem " +
                    "WHERE order_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderid);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected >= 1) {
                    result = true;
                } else {
                    throw new DatabaseException("Underligt mindre end 1 row affected med order med id = " + orderid + " blev slettet fra orderitem tabellen. (check evt. databasen for fejl)");
                }
            }

            //Derefter slettes orderen
            sql = "DELETE FROM cupcake.order " +
                    "WHERE order_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, orderid);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    result = true;
                } else {
                    throw new DatabaseException("Underligt flere/mindre end 1 row affected med order med id = " + orderid + " blev slettet fra order tabellen (check evt. databasen for fejl)");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke slette order og orderitem for order med id = " + orderid);
        }

        return result;
    }

    @Override
    public int getUserBalanceByUserId(int userId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        int result = -1;

        String sql = "SELECT balance FROM user WHERE user_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    result = rs.getInt("balance");

                    if (result == -1)
                        throw new DatabaseException("Fejl under indlæsning af user.balance fra databasen, fandt ingen user ud fra userId: " + userId);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Fejl Kunne ikke oprette forbindelse til databasen i metode: getUserBalanceByUserId");
        }

        return result;
    }

    @Override
    public boolean updateUserBalanceByUserId(int userId, int price) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        try (Connection connection = connectionPool.getConnection()) {

            String sql = "UPDATE user SET balance = balance-? " +
                    "WHERE user_id = ?;";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, price);
                ps.setInt(2, userId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    result = true;
                } else {
                    throw new DatabaseException(rowsAffected + " row affected med order med id = " + userId + " blev updated fra user tabellen. (check evt. databasen for fejl)");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke updatere user balance for user med id = " + userId);
        }

        return result;
    }

    @Override
    public Order getOrderById(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        return null;
    }

    @Override
    public List<OrderItemDTO> getOrderItemByOrderId(int orderId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

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
                    orderItemDTOList.add(new OrderItemDTO(bottom, topping, quantity, price));
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
                    java.util.Date date = rs.getDate("order_date");
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

//    private int getTotalsumByOrderId(int orderId) {
//        //WIP skrot denne metode?
//        return 0;
//    }
//
//    private int getUserIdByOrderId(int orderid) throws DatabaseException {
//        Logger.getLogger("web").log(Level.INFO, "");
//        int orderId = -1;
//
//        String sql = "SELECT user_id FROM cupcake.order WHERE order_id = ?;";
//
//        try (Connection connection = connectionPool.getConnection())
//        {
//            try (PreparedStatement ps = connection.prepareStatement(sql))
//            {
//                ps.setInt(1, orderid);
//                ResultSet rs = ps.executeQuery();
//                while (rs.next())
//                {
//                    orderId = rs.getInt("user_id");
//                    if (orderId == -1)
//                        throw new DatabaseException("Fejl under indlæsning af user_id fra databasen, fandt ingen bruger ud fra orderid: " + orderid);
//                }
//            }
//        } catch (SQLException ex)
//        {
//            throw new DatabaseException(ex, "Fejl under indlæsning af user_id fra databasen");
//        }
//
//        return orderId;
//    }

/*
//skal aligevel ikke bruges, ellers smid i usermapper
public User getUserByUserId(int userId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        User user = null;
        String sql = "SELECT * FROM user " +
                "WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int roleId = rs.getInt("role_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String email = rs.getString("email");
                    int balance = rs.getInt("balance");

                    user = new User(userId, roleId, username, password, email, balance);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Ingen bruger blev fundet med userId=" + userId);
        }
        return user;
    }
 */