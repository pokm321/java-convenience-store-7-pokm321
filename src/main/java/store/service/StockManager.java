package store.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.view.InputView;

public class StockManager {

    private final InputView inputView;
    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;
    private final Retrier retrier;

    public StockManager(
            InputView inputView, Products products, Promotions promotions, PromotionTimer timer, Retrier retrier
    ) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
        this.inputView = inputView;
        this.retrier = retrier;
    }

    public void askFreeAdditions(Orders orders) {
        for (Order order : orders.getAll()) {
            askFreeAddition(order);
        }
    }

    private void askFreeAddition(Order order) {
        int freeCount;
        if (timer.isPromotionPeriod(order) && (freeCount = getFreeCount(order)) > 0) {
            if (retrier.tryUntilSuccess(inputView::isAddingFree, order.getName(), freeCount)) {
                addFreeToOrder(order, freeCount);
            }
        }
    }

    private int getFreeCount(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int stock = products.getPromotedQuantityByName(order.getName());
        int buyGet = promotion.getBuy() + promotion.getGet();
        int promotedCount = (order.getQuantity() / buyGet) * buyGet;

        if ((order.getQuantity() - promotedCount) >= promotion.getBuy() && stock >= promotedCount + buyGet) {
            return promotedCount + buyGet - order.getQuantity();
        }
        return 0;
    }

    private void addFreeToOrder(Order order, int freeCount) {
        order.setQuantity(order.getQuantity() + freeCount);
    }

    ///////////

    public void askNotEnoughPromotionStocks(Orders orders) {
        for (Order order : orders.getAll()) {
            askNotEnoughPromotionStock(order);
        }
    }


    private void askNotEnoughPromotionStock(Order order) {
        int noPromotionCount;
        if (timer.isPromotionPeriod(order) && (noPromotionCount = getNoPromotionCount(order)) > 0) {
            if (!retrier.tryUntilSuccess(inputView::isGoingNoPromotionPrice, order.getName(), noPromotionCount)) {
                subtractNoPromotionToOrder(order, noPromotionCount);
            }
        }
    }

    private int getNoPromotionCount(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int stock = products.getPromotedQuantityByName(order.getName());
        int buyGet = promotion.getBuy() + promotion.getGet();

        int promotionsToGet = getPromotionsToGet(order, buyGet, promotion.getBuy());
        if (stock < promotionsToGet) {
            return order.getQuantity() - (stock / buyGet) * buyGet;
        }
        return 0;
    }

    private int getPromotionsToGet(Order order, int buyGet, int buy) {
        int promotionsToGet = (order.getQuantity() / buyGet) * buyGet;

        if (order.getQuantity() - promotionsToGet >= buy) {
            promotionsToGet = promotionsToGet + buyGet;
        }

        return promotionsToGet;
    }

    private void subtractNoPromotionToOrder(Order order, int noPromotionCount) {
        order.setQuantity(order.getQuantity() - noPromotionCount);
    }

    ///////////

    public Map<String, Integer> getFreeProducts(Orders orders) {
        Map<String, Integer> freeProducts = new HashMap<>();
        for (Order order : orders.getAll()) {
            if (!timer.isPromotionPeriod(order)) {
                continue;
            }

            Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
            int buyGet = promotion.getBuy() + promotion.getGet();
            int stock = products.getPromotedQuantityByName(order.getName());
            int freeCount = (Math.min(stock, order.getQuantity()) / buyGet) * promotion.getGet();

            if (freeCount != 0) {
                freeProducts.put(order.getName(), freeCount);
            }
        }
        return freeProducts;
    }

    ///////////

    public void deductOrders(Orders orders) {
        for (Order order : orders.getAll()) {
            deductOrder(order, timer.isPromotionPeriod(order));
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
