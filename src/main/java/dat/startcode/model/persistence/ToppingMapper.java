package dat.startcode.model.persistence;

import dat.startcode.model.entities.Topping;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ToppingMapper implements IToppingMapper{

    ConnectionPool connectionPool;

    public ToppingMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public Topping getToppingbyId(int topping_id) throws DatabaseException {
//        Logger.getLogger("web").log(Level.INFO, "");

        Topping topping = null;

        String sql = "SELECT * FROM cupcake.topping WHERE topping_id = ?;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, topping_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int toppingId = rs.getInt("topping_id");
                    String flavor = rs.getString("flavor");
                    int price = rs.getInt("price");
                    topping = new Topping(toppingId, flavor, price);
                } else
                {
                    throw new DatabaseException("Error. No such Topping_id");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return topping;
    }
}
