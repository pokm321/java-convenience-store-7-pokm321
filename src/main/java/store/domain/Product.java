package store.domain;

import java.util.List;

public class Product {

    public static final String DELIMITER = ",";

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String line) {
        String[] fields = getFields(line);

        this.name = fields[0];
        this.price = Integer.parseInt(fields[1]);
        this.quantity = Integer.parseInt(fields[2]);
        this.promotion = fields[3];
    }

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
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

    private String[] getFields(String line) {
        return line.split(DELIMITER);
    }
}
