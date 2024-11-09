package store;

import store.controller.StoreController;
import store.domain.Products;
import store.domain.Promotions;
import store.util.Retrier;
import store.util.md.MdPaths;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Products products = new Products(MdPaths.PRODUCTS.getPath());
        Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
        Retrier retrier = new Retrier();

        StoreController controller = new StoreController(inputView, outputView, products, promotions, retrier);

        controller.run();
    }
}
