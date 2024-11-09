package store.domain.input;

import store.domain.Products;
import store.view.InputErrors;

public class Order {

    private final String name;
    private int quantity;

    public Order(String name, int quantity, Products products) {
        validate(name, quantity, products);

        this.name = name;
        this.quantity = quantity;
    }

    private void validate(String name, int quantity, Products products) {
        validateNameExists(name, products);
        validateQuantityEnough(name, quantity, products);
    }

    private void validateNameExists(String name, Products products) {
        if (products.getProductsByName(name).isEmpty()) {
            throw new IllegalArgumentException(InputErrors.INVALID_NAME.getMessage());
        }
    }

    private void validateQuantityEnough(String name, int quantity, Products products) {
        if (products.getQuantityByName(name) < quantity) {
            throw new IllegalArgumentException(InputErrors.INVALID_QUANTITY.getMessage());
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
