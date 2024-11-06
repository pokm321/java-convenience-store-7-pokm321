package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductsTest {

    @Test
    void 제품_추가_테스트() {
        Products products = new Products();
        products.addProduct("콜라,1000,5,MD추천");
        products.addProduct("사이다,2000,10,null");

        assertThat(products.getAll().get(0).getPrice())
                .isEqualTo(1000);
        assertThat(products.getAll().get(1).getPromotion())
                .isEqualTo("null");
    }
}
