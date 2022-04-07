package dat.startcode.model.persistence;

import dat.startcode.model.DTO.OrderItemDTOT;
import dat.startcode.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapperT implements IOrderMapperT
{
    ConnectionPool connectionPool;

    public OrderMapperT(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<OrderItemDTOT> getOrderItemByOrderId(int orderId) throws DatabaseException
    {
        List<OrderItemDTOT> orderItemDTOList = new ArrayList<>();

        String sql = "SELECT cupcake.orderitem.order_id, bottom.flavor as bottom, topping.flavor as topping, quantity, SUM(quantity*(topping.price + bottom.price)) as price " +
                "FROM orderitem " +
                "inner join bottom " +
                "using (bottom_id) " +
                "inner join topping " +
                "using (topping_id) " +
                "where order_id = ? " +
                "group by orderitem_id";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    String bottom = rs.getString("bottom");
                    String topping = rs.getString("topping");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    orderItemDTOList.add(new OrderItemDTOT(bottom, topping, quantity, price));
                } else
                {
                    throw new DatabaseException("Error. Kan ikke finde order.");
                }
            }
        } catch (SQLException | DatabaseException ex)
        {
            throw new DatabaseException(ex, "Fejl i databasen.");
        }
        return orderItemDTOList;
    }

    @Override
    public int getTotalSumByOrederId()
    {
        return 0;
    }
}
