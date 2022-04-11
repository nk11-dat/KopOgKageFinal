package dat.startcode.model.DTO;

import java.util.Date;

public class OrderInformationDTO
{
    private String name;
    int orderId;
    Date date;

    public OrderInformationDTO(int orderId, String name, Date date)
    {
        this.name = name;
        this.orderId = orderId;
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
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
        return "OrderInformationDTO{" +
                "name='" + name + '\'' +
                ", orderId=" + orderId +
                ", date=" + date +
                '}';
    }
}
