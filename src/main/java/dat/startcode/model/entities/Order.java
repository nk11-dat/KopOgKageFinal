package dat.startcode.model.entities;

import java.time.LocalDate;
import java.util.Date;

public class Order
{
    private int orderId;
    private int userId;
    private boolean status;
    private int totalSum;
    private Date date;

    public Order(int orderId, int userId, boolean status, int totalSum, Date date)
    {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalSum = totalSum;
        this.date = date;
    }

    public Order(int orderId, int userId, boolean status, int totalSum)
    {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalSum = totalSum;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public boolean getStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public int getTotalSum()
    {
        return totalSum;
    }

    public void setTotalSum(int totalSum)
    {
        this.totalSum = totalSum;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status=" + status +
                ", totalSum=" + totalSum +
                ", date=" + date +
                '}';
    }
}
