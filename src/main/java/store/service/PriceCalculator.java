package store.service;

import java.util.Map;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Orders;

public class PriceCalculator {

    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;

    private static final float MEMBERSHIP_DISCOUNT_RATE = 0.3F;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;


    public PriceCalculator(Products products, Promotions promotions, PromotionTimer timer) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
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

    public long getMembershipDiscount(Orders orders) {
        return (long) Math.min(getNoPromotionTotalPrice(orders) * MEMBERSHIP_DISCOUNT_RATE, MAX_MEMBERSHIP_DISCOUNT);
    }

    private long getNoPromotionTotalPrice(Orders orders) {
        return orders.getAll().stream().mapToInt(order -> {
            int price = products.getPriceByName(order.getName());
            if (!timer.isPromotionPeriod(order)) {
                return order.getQuantity() * price;
            }

            Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
            int buyGet = promotion.getBuy() + promotion.getGet();
            int stock = products.getPromotedQuantityByName(order.getName());
            int promotedCount = (Math.min(stock, order.getQuantity()) / buyGet) * buyGet;

            return (order.getQuantity() - promotedCount) * price;
        }).sum();
    }
}
