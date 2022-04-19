package dat.startcode.model.persistence;

import dat.startcode.model.DTO.*;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderitem;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.List;

public interface IOrderMapper {
    public List<OrderHeaderAdminDTO> getAllOrders() throws DatabaseException;
    public List<OrderItemAdminDTO> getOrdersItems(int orderId) throws DatabaseException;
    public Order getOrderById(int orderId) throws DatabaseException;
    public boolean setOrderStatusById(int orderId) throws DatabaseException;
    public boolean deleteOrderByOrderId(int orderId) throws DatabaseException;
    public int getUserBalanceByUserId(int userId) throws DatabaseException;
    public boolean updateUserBalanceByUserId(int userId, int price)throws DatabaseException;
    public UserIdAndTotalSumDTO getUserIdAndTotalSumByOrderId(int orderId) throws DatabaseException;

    public List<OrderItemDTO> getOrderItemByOrderId(int orderId) throws DatabaseException;
    public int getTotalSumByOrderId(int orderId) throws DatabaseException;
    public Order confirmOrder(int userId, boolean status, int totalSum) throws DatabaseException;
    public OrderInformationDTO showOrderInformation(int orderId) throws DatabaseException;
    public Orderitem insertOrderItem(int orderId, int toppingId, int bottomId, int quantity) throws DatabaseException;
}
