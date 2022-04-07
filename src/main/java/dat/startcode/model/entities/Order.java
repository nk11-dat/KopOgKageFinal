package dat.startcode.model.entities;

import java.util.Date;

public class Order
{
    private int orderId;
    private int userId;
    private int status;
    private int totalSum;
    private Date date;

    public Order(int orderId, int userId, int status, int totalSum, Date date)
    {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.totalSum = totalSum;
        this.date = date;
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

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
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
