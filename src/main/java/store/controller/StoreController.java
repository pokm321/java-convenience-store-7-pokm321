package store.controller;

import store.domain.MdPaths;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.QuantityChecker;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Products products = new Products(MdPaths.PRODUCTS.getPath());
        Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());

        outputView.printStock(products, promotions);
        tryUntilSuccess(() -> {
            Orders orders = new Orders(inputView.readItem());
            QuantityChecker checker = new QuantityChecker(products, orders);
            checker.checkEnoughQuantity();
        });
        
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
