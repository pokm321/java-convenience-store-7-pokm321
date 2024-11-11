package store.service.stockmanager;

import java.util.List;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.dto.receipt.ReceiptFreeProductDTO;
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

    public List<ReceiptFreeProductDTO> createFreeProductDTOs(Orders orders) {
        return orders.getAll().stream()
                .filter(timer::isPromotionPeriod)
                .map(this::getFreeProductDTO)
                .filter(freeProduct -> freeProduct.getQuantity() != 0)
                .toList();
    }

    private ReceiptFreeProductDTO getFreeProductDTO(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int buyGet = promotion.getBuy() + promotion.getGet();
        int promotionStock = products.getPromotionQuantityByName(order.getName());
        int freeCount = (Math.min(promotionStock, order.getQuantity()) / buyGet) * promotion.getGet();

        return new ReceiptFreeProductDTO(order.getName(), freeCount);
    }
}
