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


}
