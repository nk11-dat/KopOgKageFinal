package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderItemOverviewAdminDTO;
import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.DTO.UserIdTotalSumDTO;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.User;
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
    public boolean setOrderStatusById(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        //Hent user_id og total_sum ud fra order tabellen
        UserIdTotalSumDTO userIdAndTotalSum = getUserIdAndTotalSumByOrderId(orderId);
//        int userId = getUserIdByOrderId(orderId); //hent userid ud fra den valgte order(orderid)
//        int totalSum = getTotalsumByOrderId(orderId); //hent order.totalsum ud fra valgte order(orderid)

        if (updateUserBalanceByUserId(userIdAndTotalSum.getUserId(), userIdAndTotalSum.getTotalSum())) //hvis en bruger kunne betale
        {
            String sql = "UPDATE cupcake.order SET status = 1 WHERE cupcake.order.order_id = ?;";

            try (Connection connection = connectionPool.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setInt(1, orderId);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected == 1){
                        result = true;
                    } else {
                        throw new DatabaseException("Mere/mindre end 1 row affected da order med id = " + orderId + ". Skulle updates! (check evt. databasen for fejl)");
                    }
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Kunne ikke opdatere order status for order med id = " + orderId);
            }
        }

        return result;
    }

    public UserIdTotalSumDTO getUserIdAndTotalSumByOrderId(int orderid) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        UserIdTotalSumDTO idAndSum = null;

        String sql = "SELECT user_id, total_sum FROM cupcake.order WHERE order_id = ?;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderid);
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int userId = rs.getInt("user_id");
                    int totalSum = rs.getInt("total_sum");

                    idAndSum = new UserIdTotalSumDTO(userId, totalSum);
                    if (idAndSum == null)
                        throw new DatabaseException("Fejl under indlæsning af user_id/total_sum fra databasen, fandt ingen order ud fra orderid: " + orderid);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl Kunne ikke oprette forbindelse til databasen i metode: getUserIdAndTotalSumByOrderId");
        }

        return idAndSum;
    }

    private boolean updateUserBalanceByUserId(int userid, int price) throws DatabaseException {
        //TODO: Burde denne ligge i UserMapper istedet?...
            //burde den vel nok, så skal der bare oprettes et UserMapper objekt når man skal kalder metoden fra andre Mappere.
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;

        String sql = "UPDATE user SET balance = IF(balance >= ?, balance-?, balance) WHERE user_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, price);
                ps.setInt(2, price);
                ps.setInt(3, userid);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke opdatere user balance for userid = " + userid);
        }

        return result;
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
                if (rowsAffected >= 1){
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
                if (rowsAffected == 1){
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
    public Order getOrderById(int orderId) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        return null;
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
public User getUserByUserId(int userid) throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");
        User user = null;
        String sql = "SELECT * FROM user " +
                "WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userid);
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
            throw new DatabaseException(ex, "Ingen bruger blev fundet med userid=" + userid);
        }
        return user;
    }
 */