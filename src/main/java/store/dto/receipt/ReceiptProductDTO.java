package store.dto.receipt;

public class ReceiptProductDTO {

    private final String name;
    private final int quantity;
    private final long totalPrice;

    public ReceiptProductDTO(String name, int quantity, long totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }
}
