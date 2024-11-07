package store.service;

import store.domain.Products;
import store.domain.input.InputErrors;
import store.domain.input.Orders;

public class PriceCalculator {

    private final Products products;

    public PriceCalculator(Products products) {
        this.products = products;
    }

    public long getRawTotalPrice(Orders orders) {
        return orders.getAll().stream()
                .mapToLong(order -> order.getQuantity() * getEachPrice(order.getName()))
                .sum();
    }

    private long getEachPrice(String name) {
        return products.getAll().stream()
                .filter(product -> product.getName().equals(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(InputErrors.INVALID_NAME.getMessage()))
                .getPrice();
    }

}
