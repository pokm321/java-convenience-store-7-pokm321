package store.service.stockmanager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.service.PromotionTimer;

public class FreeProductsChecker {

    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;

    public FreeProductsChecker(Products products, Promotions promotions, PromotionTimer timer) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
    }

    public Map<String, Integer> check(Orders orders) {
        return orders.getAll().stream()
                .filter(timer::isPromotionPeriod)
                .map(this::getFreeProduct)
                .filter(entry -> entry.getValue() != 0)
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Entry<String, Integer> getFreeProduct(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int buyGet = promotion.getBuy() + promotion.getGet();
        int promotionStock = products.getPromotedQuantityByName(order.getName());
        int freeCount = (Math.min(promotionStock, order.getQuantity()) / buyGet) * promotion.getGet();

        return Map.entry(order.getName(), freeCount);
    }
}
