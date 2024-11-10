package store.service.ordermanager;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.service.PromotionTimer;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.util.md.MdReader;
import store.view.InputView;

public class NonPromotionAskerTest {

    InputView inputView = new InputView();
    Products products = new Products();
    Promotions promotions = new Promotions();
    MdReader reader = new MdReader();
    PromotionTimer timer = new PromotionTimer(products, promotions);
    Retrier retrier = new Retrier();
    NonPromotionAsker nonPromotionAsker = new NonPromotionAsker(inputView, products, promotions, timer, retrier);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 프로모션_비적용_물품_그대로_테스트() throws NoSuchElementException {
        Orders orders = new Orders("[초코바-8],[감자칩-8],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));
        Console.close();
        System.setIn(new ByteArrayInputStream("Y\nY\nY".getBytes()));

        nonPromotionAsker.ask(orders);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("초코바")).toList().getFirst()
                .getQuantity()).isEqualTo(8);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("감자칩")).toList().getFirst()
                .getQuantity()).isEqualTo(8);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("사이다")).toList().getFirst()
                .getQuantity()).isEqualTo(8);

    }

    @Test
    void 프로모션_비적용_물품_제외_테스트() throws NoSuchElementException {
        Orders orders = new Orders("[초코바-8],[감자칩-8],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));
        Console.close();
        System.setIn(new ByteArrayInputStream("N\nN\nN".getBytes()));

        nonPromotionAsker.ask(orders);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("초코바")).toList().getFirst()
                .getQuantity()).isEqualTo(4);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("감자칩")).toList().getFirst()
                .getQuantity()).isEqualTo(8);
        assertThat(orders.getAll().stream().filter(o -> o.getName().equals("사이다")).toList().getFirst()
                .getQuantity()).isEqualTo(6);

    }
}
