package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PromotionsTest {

    @Test
    void 제품_추가_테스트() {
        Promotions promotions = new Promotions();
        promotions.addItem("탄산2+1,2,1,2024-01-01,2024-12-31");
        promotions.addItem("MD추천상품,1,1,2024-01-01,2024-12-31");

        assertThat(promotions.getAll().get(0).getBuy())
                .isEqualTo(2);
        assertThat(promotions.getAll().get(1).getStartDate())
                .isEqualTo("2024-01-01");
    }
}
