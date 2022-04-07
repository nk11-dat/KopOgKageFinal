package dat.startcode.model.persistence;

import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements IUserMapper
{
    ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public User login(String email, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM user " +
                "where email = ? and password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    int roleId = rs.getInt("role_id");
                    String username = rs.getString("username");
                    int balance = rs.getInt("balance");
                    user = new User(userId, roleId, username, password, email, balance);
                } else {
                    throw new DatabaseException("Wrong username or password");
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    //TODO wooooooooooo

    @Override
    public User createUser(String username, String password, String email, int balance) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        int newId = 0;
        String sql = "insert into user (username, password, email, balance) values (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setInt(4, balance);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {

                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next()) {
                    newId = idResultset.getInt(1);
                    user = new User(newId,1, username, password, email, balance);
                } else
                {
                    throw new DatabaseException("The user with username = " + username + " could not be inserted into the database");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
        return user;

    }
}

//    String sql = "SELECT user_id, role_id, username, password, email, balance, r.name " +
//            "FROM user inner join role r using (role_id) " +
//            "where email = ? and password = ?";
