package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.stockmanager.FreeProductsChecker;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.util.md.MdReader;
import store.view.InputView;

public class PriceCalculatorTest {

    InputView inputView = new InputView();
    MdReader reader = new MdReader();
    Products products = new Products();
    Promotions promotions = new Promotions();
    Retrier retrier = new Retrier();
    PromotionTimer timer = new PromotionTimer(products, promotions);
    FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);
    PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 총구매액_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]", products);
        PromotionTimer timer = new PromotionTimer(products, promotions);
        PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);
        assertThat(calculator.getRawTotalPrice(orders)).isEqualTo(21000L);
    }

    @Test
    void 행사할인_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        Map<String, Integer> freeProducts = freeProductsChecker.check(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(0L);

        timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));
        freeProducts = freeProductsChecker.check(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(4400L);

        timer.setTime(LocalDateTime.parse("2024-11-08T01:20:30"));
        freeProducts = freeProductsChecker.check(orders);
        assertThat(calculator.getPromotionDiscount(freeProducts)).isEqualTo(5900L);
    }

    @Test
    void 멤버십할인_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-10]", products);
        System.setIn(new ByteArrayInputStream("Y\nY\nY".getBytes()));

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        assertThat(calculator.askMembershipDiscount(orders)).isEqualTo(8000L);

        timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));
        assertThat(calculator.askMembershipDiscount(orders)).isEqualTo(7560L);

        timer.setTime(LocalDateTime.parse("2024-11-08T01:20:30"));
        assertThat(calculator.askMembershipDiscount(orders)).isEqualTo(5760L);
    }
}
