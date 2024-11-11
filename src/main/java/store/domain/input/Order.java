package store.domain.input;

import store.domain.Products;
import store.view.ViewErrors;

public class Order {

    private static final int MIN_QUANTITY = 0;

    private final String name;
    private int quantity;

    public Order(String name, int quantity, Products products) {
        validate(name, quantity, products);

        this.name = name;
        this.quantity = quantity;
    }

    private void validate(String name, int quantity, Products products) {
        validateRange(quantity);
        validateNameExists(name, products);
        validateQuantityEnough(name, quantity, products);
    }

    private void validateRange(int quantity) {
        if (quantity <= MIN_QUANTITY) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateNameExists(String name, Products products) {
        if (products.getProductsByName(name).isEmpty()) {
            throw new IllegalArgumentException(ViewErrors.INVALID_NAME.getMessage());
        }
    }

    private void validateQuantityEnough(String name, int quantity, Products products) {
        if (products.getQuantityByName(name) < quantity) {
            throw new IllegalArgumentException(ViewErrors.INVALID_QUANTITY.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
