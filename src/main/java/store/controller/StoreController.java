package store.controller;

import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.StockManager;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Products products;
    private final Promotions promotions;
    private Orders orders;

    public StoreController(InputView inputView, OutputView outputView, Products products, Promotions promotions) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.products = products;
        this.promotions = promotions;
    }

    public void run() {
        outputView.printStock(products, promotions);
        tryUntilSuccess(() -> orders = new Orders(inputView.readItem(), products));

        PriceCalculator calculator = new PriceCalculator(products);
        PromotionTimer timer = new PromotionTimer(products, promotions);
        StockManager manager = new StockManager(products, timer);

        System.out.println(calculator.getRawTotalPrice(orders));

        manager.deductOrders(orders, timer.getNow());
        outputView.printStock(products, promotions);
    }

    private void tryUntilSuccess(Runnable function) {
        while (true) {
            try {
                function.run();
                break;
            } catch (IllegalArgumentException error) {
                outputView.printError(error.getMessage());
            }
        }
    }
}
