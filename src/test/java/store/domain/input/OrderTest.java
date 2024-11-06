package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderTest {

    @Test
    void 객체_생성_테스트() {
        Order order = new Order("[콜라-123]");

        assertThat(order.getName()).isEqualTo("콜라");
        assertThat(order.getQuantity()).isEqualTo(123);
    }

    @Test
    void null_예외_테스트() {
        assertThatThrownBy(() -> {
            Order order = new Order(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "[콜라-]", "[-]", "[]", "[-32]"})
    void 빈_값_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Order order = new Order(rawOrder);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라-123", "[콜라-123", "콜라-123]"})
    void bracket_제거_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Order order = new Order(rawOrder);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-123-10]", "[콜라]"})
    void 변수_개수_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Order order = new Order(rawOrder);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void int_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            Order order = new Order("[콜라-" + field + "]");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-0]", "콜라--3"})
    void 양수가_아닐때_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Order order = new Order(rawOrder);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
