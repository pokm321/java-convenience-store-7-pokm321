package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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
}
