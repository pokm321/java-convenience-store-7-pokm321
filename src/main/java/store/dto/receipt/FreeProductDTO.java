package store.dto.receipt;

public class FreeProductDTO {

    private final String name;
    private final int quantity;

    public FreeProductDTO(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
