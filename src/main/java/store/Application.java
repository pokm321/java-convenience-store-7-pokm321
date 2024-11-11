package store;

import store.controller.StoreController;
import store.domain.Products;
import store.domain.Promotions;
import store.service.PromotionTimer;
import store.service.StockDeductor;
import store.service.asker.FreeAdditionAsker;
import store.service.asker.MembershipAsker;
import store.service.asker.NonPromotionAsker;
import store.service.dtocreator.FreeProductsChecker;
import store.service.dtocreator.OrdersChecker;
import store.service.dtocreator.PriceCalculator;
import store.service.dtocreator.StockChecker;
import store.util.Retrier;
import store.util.md.MdReader;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        MdReader reader = new MdReader();
        Products products = new Products();
        Promotions promotions = new Promotions();
        Retrier retrier = new Retrier();
        PromotionTimer timer = new PromotionTimer(products, promotions);

        FreeAdditionAsker freeAdditionAsker = new FreeAdditionAsker(inputView, products, promotions, timer, retrier);
        NonPromotionAsker nonPromotionAsker = new NonPromotionAsker(inputView, products, promotions, timer, retrier);
        MembershipAsker membershipAsker = new MembershipAsker(inputView, retrier);
        PriceCalculator calculator = new PriceCalculator(products, promotions, timer, membershipAsker);
        StockChecker stockChecker = new StockChecker(products);
        OrdersChecker ordersChecker = new OrdersChecker(products);
        FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);
        StockDeductor deductor = new StockDeductor(products, timer);

        StoreController controller = new StoreController(inputView, outputView, reader, products, promotions, retrier,
                timer, calculator, freeAdditionAsker, nonPromotionAsker, stockChecker, ordersChecker,
                freeProductsChecker, deductor);

        controller.run();
    }
}
