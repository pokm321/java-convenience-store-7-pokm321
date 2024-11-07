package store.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import store.domain.Product;
import store.domain.Products;
import store.domain.input.Order;
import store.domain.input.Orders;

public class QuantityChecker {

    Products products;
    Orders orders;

    public QuantityChecker(Products products, Orders orders) {
        this.products = products;
        this.orders = orders;
    }

    public boolean isEnoughQuantity() {
        Map<String, Integer> orders_pairs = getPairs(orders.getAll(), Order::getName, Order::getQuantity);
        Map<String, Integer> products_pairs = getPairs(products.getAll(), Product::getName, Product::getQuantity);

        return orders_pairs.keySet().stream()
                .allMatch(orderName -> products_pairs.get(orderName) >= orders_pairs.get(orderName));
    }

    private <T> Map<String, Integer> getPairs(List<T> items, Function<T, String> getName,
                                              Function<T, Integer> getQuantity) {
        Map<String, Integer> name_quantity = new HashMap<>();
        items.forEach(item ->
                name_quantity.merge(getName.apply(item), getQuantity.apply(item), Integer::sum)
        );

        return name_quantity;
    }

}
