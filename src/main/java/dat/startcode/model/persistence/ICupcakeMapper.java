package dat.startcode.model.persistence;

import dat.startcode.model.DTO.CupcakeDTO;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;

public interface ICupcakeMapper {


    public List<Topping> getToppings() throws DatabaseException, SQLException;
}
