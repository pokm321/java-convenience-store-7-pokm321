package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.util.md.MdPaths;

public class StockManagerTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());
    Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());
    PromotionTimer timer = new PromotionTimer(products, promotions);
    StockManager manager = new StockManager(products, timer);

    @Test
    void 여러_주문_기능_테스트() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3]");
        assertThat(products.getProductsByName("초코바").stream()
                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);

        LocalDateTime fakeTime = LocalDateTime.parse("2024-05-05T23:59:59");
        manager.deductOrders(orders, fakeTime);

        assertThat(products.getProductsByName("초코바").stream()
                .filter(p -> p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(4);

        assertThat(products.getProductsByName("초코바").stream()
                .filter(p -> !p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(0);

        assertThat(products.getProductsByName("감자칩").stream()
                .filter(p -> p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(2);

        assertThat(products.getProductsByName("감자칩").stream()
                .filter(p -> !p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(5);
    }
}
