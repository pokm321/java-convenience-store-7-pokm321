package store.service;

import store.domain.Products;
import store.domain.Promotion;
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

    public long getPromotedTotalPrice(Orders orders, PromotionTimer timer) {
        return orders.getAll().stream()
                .mapToLong(order -> {
                    int orderPrice = order.getQuantity() * products.getPriceByName(order.getName());
                    if (timer.isPromotionPeriod(order)) {
                        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
                        orderPrice =
                                orderPrice * promotion.getBuy() / (promotion.getBuy() + promotion.getGet());
                    }
                    return orderPrice;
                }).sum();
    }

}
