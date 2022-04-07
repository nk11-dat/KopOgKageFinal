package dat.startcode.model.entities;

public class Topping {
    private int toppingId;
    private String flavor;
    private int price;

    public Topping(int toppingId, String flavor, int price) {
        this.toppingId = toppingId;
        this.flavor = flavor;
        this.price = price;
    }

    public int getToppingId() {
        return toppingId;
    }

    public void setToppingId(int toppingId) {
        this.toppingId = toppingId;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "toppingId=" + toppingId +
                ", flavor='" + flavor + '\'' +
                ", price=" + price +
                '}';
    }
}

