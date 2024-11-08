package store.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdPaths;

public class PromotionTimerTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());

    @Test
    void 프로모션_시간_체크_테스트() {
        Order order = new Order("사이다", 5);

        LocalDateTime fakeTime = LocalDateTime.parse("2023-05-08T01:20:30");
        PromotionTimer timer = new PromotionTimer(products, promotions, fakeTime);
        assertFalse(timer.isPromotion(order));

        fakeTime = LocalDateTime.parse("2024-12-31T23:59:59");
        timer = new PromotionTimer(products, promotions, fakeTime);
        assertTrue(timer.isPromotion(order));

        fakeTime = LocalDateTime.parse("2024-01-01T00:00:01");
        timer = new PromotionTimer(products, promotions, fakeTime);
        assertTrue(timer.isPromotion(order));
    }

}
