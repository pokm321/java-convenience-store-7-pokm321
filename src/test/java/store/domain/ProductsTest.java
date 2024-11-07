package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.util.md.MdPaths;

public class ProductsTest {
    private static final String productsPath = "src/main/resources/products.md";

    @Test
    void 제품_추가_테스트() {
        Products products = new Products(MdPaths.PRODUCTS.getPath());

        assertThat(products.getAll().get(0).getName())
                .isEqualTo("콜라");
        assertThat(products.getAll().get(1).getPromotion())
                .isEqualTo("null");
        assertThat(products.getAll().get(4).getName())
                .isEqualTo("오렌지주스");
    }
}
