package store.controller;

import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.QuantityChecker;
import store.service.StockManager;
import store.util.md.MdPaths;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private Orders orders;
    private QuantityChecker checker;

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Products products = new Products(MdPaths.PRODUCTS.getPath());
        Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());

        outputView.printStock(products, promotions);
        tryUntilSuccess(() -> {
            orders = new Orders(inputView.readItem());
            checker = new QuantityChecker(products);
            checker.checkEnoughQuantity(orders);
        });

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
