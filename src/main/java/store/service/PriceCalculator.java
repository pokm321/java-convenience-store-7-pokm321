package store.service;

import store.domain.Products;
import store.domain.input.Orders;

public class PriceCalculator {

    private final Products products;

    public PriceCalculator(Products products) {
        this.products = products;
    }

    public long getRawTotalPrice(Orders orders) {
        return orders.getAll().stream()
                .mapToLong(order -> (long) order.getQuantity() * products.getPriceByName(order.getName()))
                .sum();
    }

}
