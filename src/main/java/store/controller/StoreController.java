package store.controller;

import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;
import store.service.MdReader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";

    private final InputView inputView;
    private final OutputView outputView;

    public StoreController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        MdReader reader = new MdReader(productsPath);
        Products products = reader.addItemsTo(new Products());

        reader = new MdReader(promotionsPath);
        Promotions promotions = reader.addItemsTo(new Promotions());

        outputView.printStock(products, promotions);
        Order order = new Order(inputView.readItem());
    }
}
