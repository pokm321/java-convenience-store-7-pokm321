package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.util.Retrier;
import store.util.md.MdPaths;
import store.view.InputView;

public class StockManagerTest {

    InputView inputView = new InputView();
    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
    Retrier retrier = new Retrier();
    PromotionTimer timer = new PromotionTimer(products, promotions, DateTimes.now());
    StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

    @Test
    void 여러_주문_기능_테스트() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3]", products);
        assertThat(products.getProductsByName("초코바").stream()
                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));
        manager.deductOrders(orders);

        assertThat(products.getNullProductsByName("초코바").getFirst().getQuantity()).isEqualTo(4);
        assertThat(products.getPromotedProductsByName("초코바").getFirst().getQuantity()).isEqualTo(0);

        assertThat(products.getNullProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(2);
        assertThat(products.getPromotedProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(5);
    }

    @Test
    void 증정품_기능_테스트_1() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));

        Map<String, Integer> freeProducts = manager.getFreeProducts(orders);
        assertThat(freeProducts.get("초코바")).isEqualTo(2);
        assertThat(freeProducts.get("사이다")).isEqualTo(2);
        assertFalse(freeProducts.containsKey("감자칩"));
    }

    @Test
    void 증정품_기능_테스트_2() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-11-05T23:59:59"));

        Map<String, Integer> freeProducts = manager.getFreeProducts(orders);
        assertThat(freeProducts.get("초코바")).isEqualTo(2);
        assertThat(freeProducts.get("사이다")).isEqualTo(2);
        assertThat(freeProducts.get("감자칩")).isEqualTo(1);
    }
}
