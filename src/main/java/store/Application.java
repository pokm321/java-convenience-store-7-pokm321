package store;

import store.controller.StoreController;
import store.domain.Products;
import store.domain.Promotions;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.StockManager;
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
        PromotionTimer timer = new PromotionTimer(products, promotions);
        PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);
        StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

        StoreController controller = new StoreController(inputView, outputView, products, promotions, retrier, timer,
                calculator, manager);

        controller.run();
    }
}
