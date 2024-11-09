package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
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
    private Orders orders;

    public StoreController(InputView inputView, OutputView outputView, Products products, Promotions promotions,
                           Retrier retrier) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.products = products;
        this.promotions = promotions;
        this.retrier = retrier;
    }

    public void run() {
        outputView.printStock(products, promotions);
        retrier.tryUntilSuccess(() -> orders = new Orders(inputView.readItem(), products));

        PriceCalculator calculator = new PriceCalculator(products, promotions);
        PromotionTimer timer = new PromotionTimer(products, promotions, DateTimes.now());
        StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

        manager.askFreeAdditions(orders);
        manager.askNotEnoughPromotionStocks(orders);
        manager.deductOrders(orders);

        System.out.println(calculator.getRawTotalPrice(orders));
        outputView.printStock(products, promotions);

    }

}
