package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.io.ByteArrayInputStream;
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
    Retrier retrier = new Retrier();
    PromotionTimer timer = new PromotionTimer(products, promotions, DateTimes.now());
    StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);
    PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);

    @Test
    void 총구매액_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]", products);
        PromotionTimer timer = new PromotionTimer(products, promotions, DateTimes.now());
        PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);
        assertThat(calculator.getRawTotalPrice(orders)).isEqualTo(21000L);
    }

    @Test
    void 행사할인_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        Map<String, Integer> freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(0L);

        timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));
        freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(4400L);

        timer.setTime(LocalDateTime.parse("2024-11-08T01:20:30"));
        freeProducts = manager.getFreeProducts(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(5900L);
    }

    @Test
    void 멤버십할인_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-10]", products);
        System.setIn(new ByteArrayInputStream("Y\nY\nY".getBytes()));

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        assertThat(calculator.getMembershipDiscount(orders)).isEqualTo(8000L);

        timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));
        assertThat(calculator.getMembershipDiscount(orders)).isEqualTo(7560L);

        timer.setTime(LocalDateTime.parse("2024-11-08T01:20:30"));
        assertThat(calculator.getMembershipDiscount(orders)).isEqualTo(5760L);
    }
}
