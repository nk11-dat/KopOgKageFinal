package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderInformationDTO;
import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderitem;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.Date;
import java.util.List;

public interface IOrderMapperT
{
    public List<OrderItemDTOT> getOrderItemByOrderId(int orderId) throws DatabaseException;
    public int getTotalSumByOrderId(int orderId) throws DatabaseException;
    public Order confirmOrder(int userId, boolean status, int totalSum) throws DatabaseException;
    public OrderInformationDTO showOrderInformation(int orderId) throws DatabaseException;
    public Orderitem insertOrderItem(int orderId, int toppingId, int bottomId, int quantity) throws DatabaseException;
}
