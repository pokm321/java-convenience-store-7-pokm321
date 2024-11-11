package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class ProductsTest {

    MdReader reader = new MdReader();
    Products products = new Products();
    Promotions promotions = new Promotions();

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 제품_추가_테스트() {
        assertThat(products.getAll().get(0).getName()).isEqualTo("콜라");
        assertThat(products.getAll().get(1).getPromotion()).isEqualTo("null");
        assertThat(products.getAll().get(4).getName()).isEqualTo("오렌지주스");
    }

    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            products.addProduct(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 빈_값_예외_테스트(String line) {
        assertThatThrownBy(() -> {
            products.addProduct(line);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 값_개수_예외_테스트() {
        assertThatThrownBy(() -> {
            products.addProduct("비타민워터,1500,6,null,7");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void Integer_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            products.addProduct("비타민워터," + field + ",6,null");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
