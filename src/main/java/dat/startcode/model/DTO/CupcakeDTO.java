package dat.startcode.model.DTO;

public class CupcakeDTO {

    private int toppingId;
    private int bottomId;
    private int quantity;
    private int price;

    public CupcakeDTO(int toppingId, int bottomId, int quantity) {
        this.toppingId = toppingId;
        this.bottomId = bottomId;
        this.quantity = quantity;
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
        return "CupcakeDTO{" +
                "toppingId=" + toppingId +
                ", bottomId=" + bottomId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
