package dat.startcode.model.persistence;

import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;

public interface IToppingMapper {
    public Topping getToppingbyId(int topping_id) throws DatabaseException;
}
