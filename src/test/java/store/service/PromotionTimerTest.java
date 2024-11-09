package store.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdKeywords;

public class PromotionTimerTest {

    Products products = new Products(MdKeywords.PRODUCTS_PATH.getValue());
    Promotions promotions = new Promotions(MdKeywords.PROMOTIONS_PATH.getValue());
    PromotionTimer timer = new PromotionTimer(products, promotions);

    @Test
    void 프로모션_시간_체크_테스트() {
        Order order = new Order("사이다", 5);

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        assertFalse(timer.isPromotionPeriod(order));

        timer.setTime(LocalDateTime.parse("2024-12-31T23:59:59"));
        assertTrue(timer.isPromotionPeriod(order));

        timer.setTime(LocalDateTime.parse("2024-01-01T00:00:01"));
        assertTrue(timer.isPromotionPeriod(order));
    }

}
