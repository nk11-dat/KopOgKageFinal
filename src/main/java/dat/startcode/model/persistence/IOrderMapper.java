package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderItemOverviewAdminDTO;
import dat.startcode.model.DTO.OrderOverviewHeaderAdminDTO;
import dat.startcode.model.DTO.UserIdTotalSumDTO;
import dat.startcode.model.entities.Order;
import dat.startcode.model.exceptions.DatabaseException;

import java.util.List;

public interface IOrderMapper {
    public List<OrderOverviewHeaderAdminDTO> getAllOrders() throws DatabaseException;
    public List<OrderItemOverviewAdminDTO> getOrdersItems(int orderId) throws DatabaseException;
    public Order getOrderById(int orderId) throws DatabaseException;
    public boolean setOrderStatusById(int orderId) throws DatabaseException;
    public boolean deleteOrderByOrderId(int orderId) throws DatabaseException;
    public int getUserBalanceByUserId(int userId) throws DatabaseException;
    public boolean updateUserBalanceByUserId(int userId, int price)throws DatabaseException;
    public UserIdTotalSumDTO getUserIdAndTotalSumByOrderId(int orderId) throws DatabaseException;
}
