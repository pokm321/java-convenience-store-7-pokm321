package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.Map;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.StockManager;
import store.util.Retrier;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Products products;
    private final Promotions promotions;
    private final Retrier retrier;
    private final PromotionTimer timer;
    private final PriceCalculator calculator;
    private final StockManager manager;
    private Orders orders;

    public StoreController(InputView inputView, OutputView outputView, Products products, Promotions promotions,
                           Retrier retrier, PromotionTimer timer, PriceCalculator calculator, StockManager manager) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.products = products;
        this.promotions = promotions;
        this.retrier = retrier;
        this.timer = timer;
        this.calculator = calculator;
        this.manager = manager;
    }

    public void run() {
        boolean isShopping = true;
        while (isShopping) {
            timer.setTime(DateTimes.now());

            getOrders();
            adjustOrders();
            processOrders();

            isShopping = retrier.tryUntilSuccess(inputView::isGoingAnotherShopping);
        }
    }

    private void getOrders() {
        outputView.printStock(products, promotions);
        orders = retrier.tryUntilSuccess(() -> new Orders(inputView.readItem(), products));
    }

    private void adjustOrders() {
        manager.askFreeAdditions(orders);
        manager.askNotEnoughPromotionStocks(orders);
    }

    private void processOrders() {
        Map<String, Integer> freeProducts = manager.getFreeProducts(orders);
        outputView.printReceipt(orders.getAll(), products, freeProducts, calculator.getRawTotalPrice(orders),
                calculator.getPromotionDiscount(freeProducts), calculator.askMembershipDiscount(orders));

        manager.deductOrders(orders);
    }

}
