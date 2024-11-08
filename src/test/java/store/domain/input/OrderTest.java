package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void 객체_생성_테스트() {
        Order order = new Order("콜라", 123);

        assertThat(order.getName()).isEqualTo("콜라");
        assertThat(order.getQuantity()).isEqualTo(123);
    }
}
