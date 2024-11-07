package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdKeywords;

public class PromotionTimer {

    private final Products products;
    private final Promotions promotions;

    public PromotionTimer(Products products, Promotions promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public boolean isPromotion(Order order, LocalDateTime date) {
        String promotionName = getPromotionName(order);
        Promotion promotion = promotions.getPromotionByName(promotionName);

        LocalDateTime startDate = LocalDate.parse(promotion.getStartDate()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(promotion.getEndDate()).atStartOfDay().plusDays(1);

        return date.isAfter(startDate) && date.isBefore(endDate);
    }

    private String getPromotionName(Order order) {
        List<Product> productsOnPromotion = products.getProductsByName(order.getName()).stream()
                .filter(p -> !p.getPromotion().equals(MdKeywords.NULL.getValue())).toList();

        if (productsOnPromotion.isEmpty()) {
            return MdKeywords.NULL.getValue();
        }

        return productsOnPromotion.getFirst().getPromotion();
    }

    public LocalDateTime getNow() {
        return DateTimes.now();
    }
}
