package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.dto.receipt.FreeProductDTO;
import store.service.PriceCalculator;
import store.service.PromotionTimer;
import store.service.ordermanager.FreeAdditionAsker;
import store.service.ordermanager.NonPromotionAsker;
import store.service.stockmanager.FreeProductsChecker;
import store.service.stockmanager.OrdersChecker;
import store.service.stockmanager.StockDeductor;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.util.md.MdReader;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final InputView inputView;
    private final OutputView outputView;
    private final MdReader reader;
    private final Products products;
    private final Promotions promotions;
    private final Retrier retrier;
    private final PromotionTimer timer;
    private final PriceCalculator calculator;
    private final FreeAdditionAsker freeAsker;
    private final NonPromotionAsker nonPromotionAsker;
    private final OrdersChecker ordersChecker;
    private final FreeProductsChecker freeProductsChecker;
    private final StockDeductor deductor;
    private Orders orders;

    public StoreController(InputView inputView, OutputView outputView, MdReader reader, Products products,
                           Promotions promotions, Retrier retrier, PromotionTimer timer, PriceCalculator calculator,
                           FreeAdditionAsker freeAsker, NonPromotionAsker nonPromotionAsker,
                           OrdersChecker ordersChecker, FreeProductsChecker freeProductsChecker,
                           StockDeductor deductor) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.reader = reader;
        this.products = products;
        this.promotions = promotions;
        this.retrier = retrier;
        this.timer = timer;
        this.calculator = calculator;
        this.freeAsker = freeAsker;
        this.nonPromotionAsker = nonPromotionAsker;
        this.ordersChecker = ordersChecker;
        this.freeProductsChecker = freeProductsChecker;
        this.deductor = deductor;
    }

    public void run() {
        readMdFiles();

        boolean isShopping = true;
        while (isShopping) {
            timer.setTime(DateTimes.now());

            timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));

            getOrders();
            adjustOrders();
            processOrders();

            isShopping = retrier.tryUntilSuccess(inputView::isGoingAnotherShopping);
        }
    }

    private void readMdFiles() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    private void getOrders() {
        outputView.printStock(products);
        orders = retrier.tryUntilSuccess(() -> new Orders(inputView.readItem(), products));
    }

    private void adjustOrders() {
        freeAsker.ask(orders);
        nonPromotionAsker.ask(orders);
    }

    private void processOrders() {
        List<FreeProductDTO> freeProductDTOs = freeProductsChecker.createFreeProductDTOs(orders);
        outputView.printReceipt(ordersChecker.createProductDTOs(orders),
                freeProductsChecker.createFreeProductDTOs(orders),
                calculator.createFooterDTO(orders, freeProductDTOs));

        deductor.deductOrders(orders);
    }


}
