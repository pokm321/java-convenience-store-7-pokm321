package store.service.dtocreator;

import java.util.List;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.dto.receipt.ReceiptFooterDTO;
import store.dto.receipt.ReceiptFreeProductDTO;
import store.service.PromotionTimer;
import store.service.asker.MembershipAsker;

public class PriceCalculator {

    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;
    private final MembershipAsker membershipAsker;

    private static final float MEMBERSHIP_DISCOUNT_RATE = 0.3F;
    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public PriceCalculator(Products products, Promotions promotions, PromotionTimer timer,
                           MembershipAsker membershipAsker) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
        this.membershipAsker = membershipAsker;
    }

    public ReceiptFooterDTO createFooterDTO(Orders orders, List<ReceiptFreeProductDTO> freeProducts) {
        long totalPrice = getRawTotalPrice(orders);
        long promotionDiscount = getPromotionDiscount(freeProducts);
        long membershipDiscount = askMembershipDiscount(orders);
        return new ReceiptFooterDTO(orders.getTotalQuantity(), totalPrice, promotionDiscount, membershipDiscount,
                totalPrice + promotionDiscount + membershipDiscount);
    }

    private long getRawTotalPrice(Orders orders) {
        return orders.getAll().stream()
                .mapToLong(order -> (long) order.getQuantity() * products.getPriceByName(order.getName()))
                .sum();
    }

    private long getPromotionDiscount(List<ReceiptFreeProductDTO> freeProducts) {
        return freeProducts.stream()
                .mapToLong(freeProduct -> freeProduct.getQuantity() * products.getPriceByName(freeProduct.getName()))
                .sum() * -1;
    }

    private long askMembershipDiscount(Orders orders) {
        if (membershipAsker.askMembership()) {
            return (long) Math.min(
                    getMembershipCoveredTotalPrice(orders) * MEMBERSHIP_DISCOUNT_RATE,
                    MAX_MEMBERSHIP_DISCOUNT) * -1;
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
        int stock = products.getPromotionQuantityByName(order.getName());
        int promotedCount = (Math.min(stock, order.getQuantity()) / buyGet) * buyGet;

        return (order.getQuantity() - promotedCount) * price;
    }
}
