package store.dto.stock;

public class StockProductDTO {

    private final String name;
    private final long price;
    private final int quantity;
    private final String promotion;

    public StockProductDTO(String name, long price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }
}
