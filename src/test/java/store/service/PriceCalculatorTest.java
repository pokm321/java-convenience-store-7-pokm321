package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.util.md.MdPaths;

public class PriceCalculatorTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
    PriceCalculator calculator = new PriceCalculator(products, promotions);

    @Test
    void 가격_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]", products);
        assertThat(calculator.getRawTotalPrice(orders)).isEqualTo(21000L);
    }

    @Test
    void 프로모션_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);
        LocalDateTime fakeTime = LocalDateTime.parse("2024-05-08T01:20:30");
        PromotionTimer timer = new PromotionTimer(products, promotions, fakeTime);
        assertThat(calculator.getPromotedTotalPrice(orders, timer)).isEqualTo(15000L);

        fakeTime = LocalDateTime.parse("2024-11-08T01:20:30");
        timer = new PromotionTimer(products, promotions, fakeTime);
        assertThat(calculator.getPromotedTotalPrice(orders, timer)).isEqualTo(13500L);
    }
}
