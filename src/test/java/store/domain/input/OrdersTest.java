package store.domain.input;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.Products;
import store.domain.Promotions;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class OrdersTest {

    MdReader reader = new MdReader();
    Products products = new Products();
    Promotions promotions = new Promotions();

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getValue());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getValue());
    }

    @Test
    void 객체_생성_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]", products);

        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("초코바")).toList().getFirst()
                .getQuantity()).isEqualTo(10);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("사이다")).toList().getFirst()
                .getQuantity()).isEqualTo(9);
    }

    @Test
    void 중복_물품_기능_테스트() {
        Orders orders = new Orders("[초코바-5],[사이다-9],[초코바-3]", products);

        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("초코바")).toList().getFirst()
                .getQuantity()).isEqualTo(8);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("사이다")).toList().getFirst()
                .getQuantity()).isEqualTo(9);
    }

    @Test
    void null_값_예외_테스트() {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(null, products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "     ", "[콜라-],[사이다-4]", "[-]", "[]", "[-32]"})
    void 빈_값_예외_테스트(String input) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(input, products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공백_예외_테스트() {
        assertThatThrownBy(() -> {
            Orders orders = new Orders("[콜라-3], [사이다-4]", products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"콜라-123", "[콜라-123", "콜라-123],[사이다-4]"})
    void bracket_제거_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(rawOrder, products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-123-10],[사이다-4]", "[콜라],[사이다-4]"})
    void 변수_개수_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(rawOrder, products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "9999999999999999999999999999999"})
    void int_변환_예외_테스트(String field) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders("[콜라-" + field + "],[사이다-4]", products);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-0]", "[콜라--3],[사이다-3]"})
    void 양수가_아닐때_예외_테스트(String rawOrder) {
        assertThatThrownBy(() -> {
            Orders orders = new Orders(rawOrder, products);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
