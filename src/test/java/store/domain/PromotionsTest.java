package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.util.md.MdPaths;

public class PromotionsTest {

    @Test
    void 제품_추가_테스트() {
        Promotions promotions = new Promotions(MdPaths.PROMOTIONS.getPath());

        assertThat(promotions.getAll().get(0).getBuy())
                .isEqualTo(2);
        assertThat(promotions.getAll().get(1).getStartDate())
                .isEqualTo("2024-01-01");
        assertThat(promotions.getAll().get(2).getName())
                .isEqualTo("반짝할인");
    }
}
