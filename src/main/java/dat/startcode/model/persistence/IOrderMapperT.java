package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.List;

public interface IOrderMapperT
{
    public List<OrderItemDTOT> getOrderItemByOrderId(int orderId) throws DatabaseException;
    public int getTotalSumByOrderId(int orderId) throws DatabaseException;
}
