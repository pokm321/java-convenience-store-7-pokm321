package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void 객체_생성_테스트() {
        Product coke = new Product("콜라,99999,88,반짝할인");

        assertThat(coke.getName()).isEqualTo("콜라");
        assertThat(coke.getPrice()).isEqualTo(99999);
        assertThat(coke.getQuantity()).isEqualTo(88);
        assertThat(coke.getPromotion()).isEqualTo("반짝할인");
    }
}
