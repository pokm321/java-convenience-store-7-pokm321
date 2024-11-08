package store.service;

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
    private final LocalDateTime dateTime;

    public PromotionTimer(Products products, Promotions promotions, LocalDateTime dateTime) {
        this.products = products;
        this.promotions = promotions;
        this.dateTime = dateTime;
    }

    public boolean isPromotion(Order order) {
        String promotionName = getPromotionName(order);
        Promotion promotion = promotions.getPromotionByName(promotionName);

        LocalDateTime startDate = LocalDate.parse(promotion.getStartDate()).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(promotion.getEndDate()).atStartOfDay().plusDays(1);

        return dateTime.isAfter(startDate) && dateTime.isBefore(endDate);
    }

    private String getPromotionName(Order order) {
        List<Product> productsOnPromotion = products.getProductsByName(order.getName()).stream()
                .filter(p -> !p.getPromotion().equals(MdKeywords.NULL.getValue())).toList();

        if (productsOnPromotion.isEmpty()) {
            return MdKeywords.NULL.getValue();
        }

        return productsOnPromotion.getFirst().getPromotion();
    }
}
