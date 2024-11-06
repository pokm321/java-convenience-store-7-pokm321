package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PromotionTest {

    @Test
    void 객체_생성_테스트() {
        Promotion promotion = new Promotion("MD추천상품,1,1,2024-01-01,2024-12-31");

        assertThat(promotion.getName()).isEqualTo("MD추천상품");
        assertThat(promotion.getBuy()).isEqualTo(1);
        assertThat(promotion.getGet()).isEqualTo(1);
        assertThat(promotion.getStartDate()).isEqualTo("2024-01-01");
        assertThat(promotion.getEndDate()).isEqualTo("2024-12-31");
    }

    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            Promotion promotion = new Promotion(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 빈_값_예외_테스트(String line) {
        assertThatThrownBy(() -> {
            Promotion promotion = new Promotion(line);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 값_개수_예외_테스트() {
        assertThatThrownBy(() -> {
            Promotion promotion = new Promotion("MD추천상품,1,1,2024-12-31");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void Integer_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            Promotion promotion = new Promotion("MD추천상품,1," + field + ",2024-01-01,2024-12-31");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
