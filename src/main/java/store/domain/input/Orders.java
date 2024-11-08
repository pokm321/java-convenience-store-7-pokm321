package store.domain.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import store.domain.Product;
import store.domain.Products;

public class Orders {

    private static final String COMMA = ",";

    private List<Order> listOfOrders = new ArrayList<>();

    public Orders(String rawOrders, Products products) {
        validate(rawOrders);

        for (String rawOrder : rawOrders.split(COMMA)) {
            listOfOrders.add(new Order(rawOrder));
        }

        validateQuantity(products);
    }

    public List<Order> getAll() {
        return listOfOrders;
    }

    private void validate(String rawOrders) {
        validateNotNull(rawOrders);
        validateNotEmpty(rawOrders);
    }

    private void validateNotNull(String rawOrders) {
        if (rawOrders == null) {
            throw new IllegalArgumentException(InputErrors.OTHERS.getMessage());
        }
    }

    private void validateNotEmpty(String rawOrders) {
        if (rawOrders.isBlank()) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    public void validateQuantity(Products products) {
        if (!isEnoughQuantity(products)) {
            throw new IllegalArgumentException(InputErrors.INVALID_QUANTITY.getMessage());
        }
    }

    private boolean isEnoughQuantity(Products products) {
        Map<String, Integer> ordersMerged = getMergedPairs(listOfOrders, Order::getName, Order::getQuantity);
        Map<String, Integer> productsMerged = getMergedPairs(products.getAll(), Product::getName, Product::getQuantity);

        return ordersMerged.keySet().stream()
                .allMatch(orderName -> productsMerged.get(orderName) >= ordersMerged.get(orderName));
    }

    private <T> Map<String, Integer> getMergedPairs(List<T> items, Function<T, String> getName,
                                                    Function<T, Integer> getQuantity) {
        Map<String, Integer> nameQuantity = new HashMap<>();
        items.forEach(item ->
                nameQuantity.merge(getName.apply(item), getQuantity.apply(item), Integer::sum)
        );

        return nameQuantity;
    }

}
