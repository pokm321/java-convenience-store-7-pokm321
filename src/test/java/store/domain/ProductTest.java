package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void 객체_생성_테스트_1() {
        Product coke = new Product("콜라", 1000, 10, "반짝할인");

        assertThat(coke.getName()).isEqualTo("콜라");
        assertThat(coke.getPrice()).isEqualTo(1000);
        assertThat(coke.getQuantity()).isEqualTo(10);
        assertThat(coke.getPromotion()).isEqualTo("반짝할인");
    }

    @Test
    void 객체_생성_테스트_2() {
        Product coke = new Product("콜라,99999,88,반짝할인");

        assertThat(coke.getName()).isEqualTo("콜라");
        assertThat(coke.getPrice()).isEqualTo(99999);
        assertThat(coke.getQuantity()).isEqualTo(88);
        assertThat(coke.getPromotion()).isEqualTo("반짝할인");
    }
}
