package dat.startcode.model.entities;

public class Orderitem
{
    private int orderitemId;
    private int orderId;
    private int toppingId;
    private int bottomId;
    private int quantity;

    public Orderitem(int orderitemId, int orderId, int toppingId, int bottomId, int quantity)
    {
        this.orderitemId = orderitemId;
        this.orderId = orderId;
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.quantity = quantity;
    }

    public int getOrderitemId()
    {
        return orderitemId;
    }

    public void setOrderitemId(int orderitemId)
    {
        this.orderitemId = orderitemId;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getToppingId()
    {
        return toppingId;
    }

    public void setToppingId(int toppingId)
    {
        this.toppingId = toppingId;
    }

    public int getBottomId()
    {
        return bottomId;
    }

    public void setBottomId(int bottomId)
    {
        this.bottomId = bottomId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public String toString()
    {
        return "Orderitem{" +
                "orderitemId=" + orderitemId +
                ", orderId=" + orderId +
                ", toppingId=" + toppingId +
                ", bottomId=" + bottomId +
                ", quantity=" + quantity +
                '}';
    }
}
