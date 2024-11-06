package store.domain;

import java.util.List;

public class Product {

    public static final String DELIMITER = ",";

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String line) {
        List<String> fields = getFields(line);

        this.name = fields.get(0);
        this.price = Integer.parseInt(fields.get(1));
        this.quantity = Integer.parseInt(fields.get(2));
        this.promotion = fields.get(3);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER));
    }
}
