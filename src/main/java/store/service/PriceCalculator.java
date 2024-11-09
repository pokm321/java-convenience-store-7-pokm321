package store.service;

import java.util.Map;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;

public class PriceCalculator {

    private final Products products;
    private final Promotions promotions;

    public PriceCalculator(Products products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public long getRawTotalPrice(Orders orders) {
        return orders.getAll().stream()
                .mapToLong(order -> (long) order.getQuantity() * products.getPriceByName(order.getName()))
                .sum();
    }

    public long getPromotedDiscount(Map<String, Integer> freeProducts) {
        return freeProducts.entrySet().stream()
                .mapToLong(p -> (long) products.getPriceByName(p.getKey()) * p.getValue()).sum();
    }
}
