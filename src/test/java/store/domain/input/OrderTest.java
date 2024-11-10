package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class OrderTest {

    MdReader reader = new MdReader();
    Products products = new Products();
    Promotions promotions = new Promotions();

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 객체_생성_테스트() {
        Order order = new Order("콜라", 3, products);

        assertThat(order.getName()).isEqualTo("콜라");
        assertThat(order.getQuantity()).isEqualTo(3);
    }
}
