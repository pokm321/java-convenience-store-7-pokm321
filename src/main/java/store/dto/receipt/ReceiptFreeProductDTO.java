package store.dto.receipt;

public class ReceiptFreeProductDTO {

    private final String name;
    private final int quantity;

    public ReceiptFreeProductDTO(String name, int quantity) {
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
