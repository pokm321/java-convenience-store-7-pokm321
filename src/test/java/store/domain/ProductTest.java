package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductTest {

    @Test
    void 객체_생성_테스트() {
        Product coke = new Product("콜라,99999,88,반짝할인");

        assertThat(coke.getName()).isEqualTo("콜라");
        assertThat(coke.getPrice()).isEqualTo(99999);
        assertThat(coke.getQuantity()).isEqualTo(88);
        assertThat(coke.getPromotion()).isEqualTo("반짝할인");
    }


    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            Product product = new Product(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void 빈_값_예외_테스트(String line) {
        assertThatThrownBy(() -> {
            Product product = new Product(line);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 값_개수_예외_테스트() {
        assertThatThrownBy(() -> {
            Product product = new Product("비타민워터,1500,6,null,7");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void Integer_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            Product product = new Product("비타민워터," + field + ",6,null");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
