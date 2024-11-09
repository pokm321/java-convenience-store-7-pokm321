package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.util.md.MdKeywords;

public class OrderTest {

    Products products = new Products(MdKeywords.PRODUCTS_PATH.getValue());

    @Test
    void 객체_생성_테스트() {
        Order order = new Order("콜라", 3, products);

        assertThat(order.getName()).isEqualTo("콜라");
        assertThat(order.getQuantity()).isEqualTo(3);
    }
}
