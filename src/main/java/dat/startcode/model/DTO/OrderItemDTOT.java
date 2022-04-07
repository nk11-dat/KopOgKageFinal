package dat.startcode.model.DTO;

public class OrderItemDTOT
{
    private String bottom;
    private String topping;
    private int quantity;
    private int price;

    public OrderItemDTOT(String bottom, String topping, int quantity, int price)
    {
        this.bottom = bottom;
        this.topping = topping;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBottom()
    {
        return bottom;
    }

    public void setBottom(String bottom)
    {
        this.bottom = bottom;
    }

    public String getTopping()
    {
        return topping;
    }

    public void setTopping(String topping)
    {
        this.topping = topping;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "KundeOversigtDTO{" +
                "bottom='" + bottom + '\'' +
                ", topping='" + topping + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
