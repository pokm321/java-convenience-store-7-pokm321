package store.service.stockmanager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PromotionTimer;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class StockDeductorTest {

    Products products = new Products();
    Promotions promotions = new Promotions();
    MdReader reader = new MdReader();
    PromotionTimer timer = new PromotionTimer(products, promotions);
    StockDeductor deductor = new StockDeductor(products, timer);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getValue());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getValue());
    }

    @Test
    void 여러_주문_기능_테스트() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3]", products);
        assertThat(products.getProductsByName("초코바").stream()
                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));
        deductor.deductOrders(orders);

        assertThat(products.getNullProductsByName("초코바").getFirst().getQuantity()).isEqualTo(4);
        assertThat(products.getPromotedProductsByName("초코바").getFirst().getQuantity()).isEqualTo(0);

        assertThat(products.getNullProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(2);
        assertThat(products.getPromotedProductsByName("감자칩").getFirst().getQuantity()).isEqualTo(5);
    }

}
