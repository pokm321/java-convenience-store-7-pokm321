package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrdersTest {

    @Test
    void 객체_생성_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9999]");

        assertThat(orders.getAll().get(0).getName()).isEqualTo("초코바");
        assertThat(orders.getAll().get(1).getQuantity()).isEqualTo(9999);
    }

    @Test
    void 중복_물품_기능_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9999],[초코바-10]");

        assertThat(orders.getAll().get(0).getName()).isEqualTo("초코바");
        assertThat(orders.getAll().get(1).getQuantity()).isEqualTo(9999);
        assertThat(orders.getAll().get(2).getName()).isEqualTo("초코바");
        assertThat(orders.getAll().get(2).getQuantity()).isEqualTo(10);
    }

    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     "})
    void 빈_값_예외_테스트(String input) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(input);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
