package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
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

    @Test
    void 여러_주문_기능_테스트() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3]", products);
        assertThat(products.getProductsByName("초코바").stream()
                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);

        LocalDateTime fakeTime = LocalDateTime.parse("2024-05-05T23:59:59");
        PromotionTimer timer = new PromotionTimer(products, promotions, fakeTime);
        StockManager manager = new StockManager(inputView, products, promotions, timer, retrier);

        manager.deductOrders(orders);

        assertThat(products.getNullProductsByName("초코바").getFirst().getQuantity()).isEqualTo(4);
        assertThat(products.getPromotedProductsByName("초코바").getFirst().getQuantity()).isEqualTo(0);

        assertThat(products.getNullProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(2);
        assertThat(products.getPromotedProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(5);
    }

}
