package store.service;

import java.util.Comparator;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.util.md.MdKeywords;

public class StockManager {

    private final Products products;

    public StockManager(Products products) {
        this.products = products;
    }

    public void deductOrders(Orders orders, boolean isPromotion) {
        for (Order order : orders.getAll()) {
            deductOrder(order, isPromotion);
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
        if (isPromotion) { // 프로모션 기간중엔 프로모션 재고가 앞에 오도록
            return products.stream().sorted(Comparator.comparing(product ->
                    product.getPromotion().equals(MdKeywords.NULL.getValue())
            )).toList();
        }

        return products.stream().sorted(Comparator.comparing(product ->
                !product.getPromotion().equals(MdKeywords.NULL.getValue())
        )).toList(); // 프로모션 기간이 아닐땐 일반 재고가 앞에 오도록
    }
}
