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
    private Orders orders;

    public StoreController(InputView inputView, OutputView outputView, Products products, Promotions promotions) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.products = products;
        this.promotions = promotions;
    }

    public void run() {
        Retrier retrier = new Retrier();
        outputView.printStock(products, promotions);
        retrier.tryUntilSuccess(() -> orders = new Orders(inputView.readItem(), products));

        PriceCalculator calculator = new PriceCalculator(products, promotions);
        PromotionTimer timer = new PromotionTimer(products, promotions, DateTimes.now());
        StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

        manager.askFreeAdditions(orders);
        manager.deductOrders(orders);

        System.out.println(calculator.getRawTotalPrice(orders));
        outputView.printStock(products, promotions);

    }

}
