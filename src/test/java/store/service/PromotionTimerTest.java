package store.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class PromotionTimerTest {

    Products products = new Products();
    Promotions promotions = new Promotions();
    MdReader reader = new MdReader();
    PromotionTimer timer = new PromotionTimer(products, promotions);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 프로모션_시간_체크_테스트() {
        Order order = new Order("사이다", 5, products);

        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        assertFalse(timer.isPromotionPeriod(order));

        timer.setTime(LocalDateTime.parse("2024-12-31T23:59:59"));
        assertTrue(timer.isPromotionPeriod(order));

        timer.setTime(LocalDateTime.parse("2024-01-01T00:00:01"));
        assertTrue(timer.isPromotionPeriod(order));
    }

}
