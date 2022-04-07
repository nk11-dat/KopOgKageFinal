package dat.startcode.model.persistence;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;

public interface IBottomMapper {
    public Bottom getBottomById(int bottom_id) throws DatabaseException;
}
