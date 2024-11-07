package store.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.util.md.MdKeywords;

public class StockManager {

    private final Products products;
    private final PromotionTimer timer;

    public StockManager(Products products, PromotionTimer timer) {
        this.products = products;
        this.timer = timer;
    }

    public void deductOrders(Orders orders, LocalDateTime time) {
        for (Order order : orders.getAll()) {
            deductOrder(order, timer.isPromotion(order, time));
        }
    }

    private void deductOrder(Order order, boolean isPromotion) {
        List<Product> productsToDeduct = sortByPromotion(products.getProductsByName(order.getName()), isPromotion);

        int orderQuantity = order.getQuantity();
        for (Product product : productsToDeduct) {
            int deductAmount = Math.min(product.getQuantity(), orderQuantity);

            product.setQuantity(product.getQuantity() - deductAmount);
            orderQuantity -= deductAmount;
        }
    }

    private List<Product> sortByPromotion(List<Product> products, boolean isPromotion) {
        if (isPromotion) {
            return products.stream().sorted(Comparator.comparing(product ->
                    product.getPromotion().equals(MdKeywords.NULL.getValue())
            )).toList(); // 프로모션 기간중엔 프로모션 재고가 앞에 오도록
        }

        return products.stream().sorted(Comparator.comparing(product ->
                !product.getPromotion().equals(MdKeywords.NULL.getValue())
        )).toList(); // 프로모션 기간이 아닐땐 일반 재고가 앞에 오도록
    }
}
