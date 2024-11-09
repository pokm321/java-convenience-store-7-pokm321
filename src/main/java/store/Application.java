package store;

import store.controller.StoreController;
import store.domain.Products;
import store.domain.Promotions;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.ordermanager.FreeAdditionAsker;
import store.service.ordermanager.NonPromotionAsker;
import store.service.stockmanager.FreeProductsChecker;
import store.service.stockmanager.StockDeductor;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        Products products = new Products(MdKeywords.PRODUCTS_PATH.getValue());
        Promotions promotions = new Promotions(MdKeywords.PROMOTIONS_PATH.getValue());
        Retrier retrier = new Retrier();
        PromotionTimer timer = new PromotionTimer(products, promotions);
        PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);
        FreeAdditionAsker freeAdditionAsker = new FreeAdditionAsker(inputView, products, promotions, timer, retrier);
        NonPromotionAsker nonPromotionAsker = new NonPromotionAsker(inputView, products, promotions, timer, retrier);
        FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);
        StockDeductor deductor = new StockDeductor(products, timer);

        StoreController controller = new StoreController(inputView, outputView, products, promotions, retrier, timer,
                calculator, freeAdditionAsker, nonPromotionAsker, freeProductsChecker, deductor);

        controller.run();
    }
}
