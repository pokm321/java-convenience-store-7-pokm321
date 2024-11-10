package store.service;

import java.util.Map;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.util.Retrier;
import store.view.InputView;

public class PriceCalculator {

    private final InputView inputView;
    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;
    private final Retrier retrier;

    private static final float MEMBERSHIP_DISCOUNT_RATE = 0.3F;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public PriceCalculator(InputView inputView, Products products, Promotions promotions, PromotionTimer timer,
                           Retrier retrier) {
        this.inputView = inputView;
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
        this.retrier = retrier;
    }

    public long getRawTotalPrice(Orders orders) {
        return orders.getAll().stream()
                .mapToLong(order -> (long) order.getQuantity() * products.getPriceByName(order.getName()))
                .sum();
    }

    public long getPromotionDiscount(Map<String, Integer> freeProducts) {
        return freeProducts.entrySet().stream()
                .mapToLong(p -> (long) products.getPriceByName(p.getKey()) * p.getValue()).sum();
    }

    public long askMembershipDiscount(Orders orders) {
        if (retrier.tryUntilSuccess(inputView::isMembership)) {
            return (long) Math.min(getMembershipCoveredTotalPrice(orders) * MEMBERSHIP_DISCOUNT_RATE,
                    MAX_MEMBERSHIP_DISCOUNT);
        }
        return 0;
    }

    private long getMembershipCoveredTotalPrice(Orders orders) {
        return orders.getAll().stream().mapToLong(order -> {
            long price = products.getPriceByName(order.getName());
            if (!timer.isPromotionPeriod(order)) {
                return order.getQuantity() * price;
            }

            return getMembershipCoveredPrice(order, price);
        }).sum();
    }

    private long getMembershipCoveredPrice(Order order, long price) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int buyGet = promotion.getBuy() + promotion.getGet();
        int stock = products.getPromotedQuantityByName(order.getName());
        int promotedCount = (Math.min(stock, order.getQuantity()) / buyGet) * buyGet;

        return (order.getQuantity() - promotedCount) * price;
    }
}
