package store.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdKeywords;

public class PromotionTimer {

    private final Products products;
    private final Promotions promotions;
    private final LocalDateTime dateTime;

    public PromotionTimer(Products products, Promotions promotions, LocalDateTime dateTime) {
        this.products = products;
        this.promotions = promotions;
        this.dateTime = dateTime;
    }

    public boolean isPromotionPeriod(Order order) {
        String promotionName = products.getPromotionNameByName(order.getName());
        if (promotionName.equals(MdKeywords.NULL.getValue())) {
            return false;
        }
        Promotion promotion = promotions.getPromotion(promotionName);

        LocalDateTime startDate = LocalDate.parse(promotion.getStartDate()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(promotion.getEndDate()).atStartOfDay().plusDays(1);

        return dateTime.isAfter(startDate) && dateTime.isBefore(endDate);
    }
}
