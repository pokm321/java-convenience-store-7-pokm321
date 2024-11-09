package store.service.stockmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PromotionTimer;
import store.util.md.MdPaths;

public class FreeProductsCheckerTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
    PromotionTimer timer = new PromotionTimer(products, promotions);
    FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);

    @Test
    void 증정품_기능_테스트_1() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));

        Map<String, Integer> freeProducts = freeProductsChecker.check(orders);
        assertThat(freeProducts.get("초코바")).isEqualTo(2);
        assertThat(freeProducts.get("사이다")).isEqualTo(2);
        assertFalse(freeProducts.containsKey("감자칩"));
    }

    @Test
    void 증정품_기능_테스트_2() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-11-05T23:59:59"));

        Map<String, Integer> freeProducts = freeProductsChecker.check(orders);
        assertThat(freeProducts.get("초코바")).isEqualTo(2);
        assertThat(freeProducts.get("사이다")).isEqualTo(2);
        assertThat(freeProducts.get("감자칩")).isEqualTo(1);
    }
}
