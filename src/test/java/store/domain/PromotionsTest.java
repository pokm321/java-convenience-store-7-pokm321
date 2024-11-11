package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class PromotionsTest {

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
        assertThat(promotions.getAll().get(0).getBuy()).isEqualTo(2);
        assertThat(promotions.getAll().get(1).getStartDate()).isEqualTo("2024-01-01");
        assertThat(promotions.getAll().get(2).getName()).isEqualTo("반짝할인");
    }

    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            promotions.addPromotion(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 빈_값_예외_테스트(String line) {
        assertThatThrownBy(() -> {
            promotions.addPromotion(line);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 값_개수_예외_테스트() {
        assertThatThrownBy(() -> {
            promotions.addPromotion("MD추천상품,1,1,2024-12-31");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void Integer_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            promotions.addPromotion("MD추천상품,1," + field + ",2024-01-01,2024-12-31");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
