package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BottomMapper implements IBottomMapper{

    ConnectionPool connectionPool;

    public BottomMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public Bottom getBottomById(int bottom_id) throws DatabaseException {
//        Logger.getLogger("web").log(Level.INFO, "");

        Bottom bottom = null;

        String sql = "SELECT * FROM cupcake.bottom WHERE bottom_id = ?;";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, bottom_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int toppingId = rs.getInt("bottom_id");
                    String flavor = rs.getString("flavor");
                    int price = rs.getInt("price");
                    bottom = new Bottom(toppingId, flavor, price);
                } else
                {
                    throw new DatabaseException("Error. No such Bottom_id");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return bottom;
    }
}
