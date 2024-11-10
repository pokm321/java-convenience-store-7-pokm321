package store.service.ordermanager;

import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.service.PromotionTimer;
import store.util.Retrier;
import store.view.InputView;

public class FreeAdditionAsker {

    private final InputView inputView;
    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;
    private final Retrier retrier;

    public FreeAdditionAsker(InputView inputView, Products products, Promotions promotions, PromotionTimer timer,
                             Retrier retrier) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
        this.inputView = inputView;
        this.retrier = retrier;
    }

    public void ask(Orders orders) {
        for (Order order : orders.getAll()) {
            askFreeAddition(order);
        }
    }

    private void askFreeAddition(Order order) {
        int freeCount;
        if (timer.isPromotionPeriod(order) && (freeCount = getFreeCount(order)) > 0) {
            if (retrier.tryUntilSuccess(inputView::isAddingFree, order.getName(), freeCount)) {
                order.setQuantity(order.getQuantity() + freeCount);
            }
        }
    }

    private int getFreeCount(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int promotionStock = products.getPromotionQuantityByName(order.getName());
        int buyGet = promotion.getBuy() + promotion.getGet();
        int promotedCount = (order.getQuantity() / buyGet) * buyGet;

        if ((order.getQuantity() - promotedCount) >= promotion.getBuy() && promotionStock >= promotedCount + buyGet) {
            return promotedCount + buyGet - order.getQuantity();
        }
        return 0;
    }
}
