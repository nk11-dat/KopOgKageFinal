package dat.startcode.model.persistence;

import dat.startcode.model.DTO.CupcakeDTO;
import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


public class CupcakeMapper implements ICupcakeMapper{

    private ConnectionPool connectionPool;

    public CupcakeMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Topping> getToppings() throws DatabaseException, SQLException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<Topping> getToppings = new ArrayList<>();

        String sql = "SELECT * FROM cupcake.topping;";


        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String flavor = rs.getString("flavor");
                    int price = rs.getInt("price");
                    int toppingId = rs.getInt("topping_id");
                    Topping newTopping = new Topping(toppingId, flavor, price);
                    getToppings.add(newTopping);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex,"Fejl under indlæsning af 'topping' fra databasen");
        }
        return getToppings;
    }

    public List<Bottom> getBottoms() throws DatabaseException {
        Logger.getLogger("web").log(Level.INFO, "");

        List<Bottom> getBottoms = new ArrayList<>();

        String sql = "select * "+
                "from bottom";

        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {

                    String flavor = rs.getString("flavor");
                    int price = rs.getInt("price");
                    int bottomId = rs.getInt("bottom_id");
                    Bottom newBottom = new Bottom(bottomId, flavor, price);
                    getBottoms.add(newBottom);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex,"Fejl under indlæsning af 'topping' fra databasen");
        }
        return getBottoms;
    }

}
