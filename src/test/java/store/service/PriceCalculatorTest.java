package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.util.Retrier;
import store.util.md.MdPaths;
import store.view.InputView;

public class PriceCalculatorTest {

    InputView inputView = new InputView();
    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
    PriceCalculator calculator = new PriceCalculator(products, promotions);
    Retrier retrier = new Retrier();

    @Test
    void 총구매액_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]", products);
        assertThat(calculator.getRawTotalPrice(orders)).isEqualTo(21000L);
    }

    @Test
    void 행사할인_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);

        LocalDateTime fakeTime = LocalDateTime.parse("2023-05-08T01:20:30");
        PromotionTimer timer = new PromotionTimer(products, promotions, fakeTime);
        StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

        Map<String, Integer> freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotedDiscount(freeProducts)).isEqualTo(0L);

        fakeTime = LocalDateTime.parse("2024-05-08T01:20:30");
        timer = new PromotionTimer(products, promotions, fakeTime);
        manager = new StockManager(inputView, products, promotions, timer, retrier);
        freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotedDiscount(freeProducts)).isEqualTo(4400L);

        fakeTime = LocalDateTime.parse("2024-11-08T01:20:30");
        timer = new PromotionTimer(products, promotions, fakeTime);
        manager = new StockManager(inputView, products, promotions, timer, retrier);
        freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotedDiscount(freeProducts)).isEqualTo(5900L);
    }
}
