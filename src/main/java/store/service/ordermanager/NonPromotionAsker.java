package store.service.ordermanager;

import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.input.Order;
import store.domain.input.Orders;
import store.service.PromotionTimer;
import store.util.Retrier;
import store.view.InputView;

public class NonPromotionAsker {

    private final InputView inputView;
    private final Products products;
    private final Promotions promotions;
    private final PromotionTimer timer;
    private final Retrier retrier;

    public NonPromotionAsker(InputView inputView, Products products, Promotions promotions,
                             PromotionTimer timer,
                             Retrier retrier) {
        this.products = products;
        this.promotions = promotions;
        this.timer = timer;
        this.inputView = inputView;
        this.retrier = retrier;
    }


    public void ask(Orders orders) {
        for (Order order : orders.getAll()) {
            askNotEnoughPromotionStock(order);
        }
    }

    private void askNotEnoughPromotionStock(Order order) {
        int noPromotionCount;
        if (timer.isPromotionPeriod(order) && (noPromotionCount = getNoPromotionCount(order)) > 0) {
            if (!retrier.tryUntilSuccess(inputView::isGoingNoPromotionPrice, order.getName(), noPromotionCount)) {
                order.setQuantity(order.getQuantity() - noPromotionCount);
            }
        }
    }

    private int getNoPromotionCount(Order order) {
        Promotion promotion = promotions.getPromotion(products.getPromotionNameByName(order.getName()));
        int promotionStock = products.getPromotedQuantityByName(order.getName());
        int buyGet = promotion.getBuy() + promotion.getGet();
        int promotionsToGet = getPromotionsToGet(order, buyGet, promotion.getBuy());

        if (promotionStock < promotionsToGet) {
            return order.getQuantity() - (promotionStock / buyGet) * buyGet;
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
}
