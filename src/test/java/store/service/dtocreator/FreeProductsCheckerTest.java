package store.service.dtocreator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.dto.receipt.ReceiptFreeProductDTO;
import store.service.PromotionTimer;
import store.util.md.MdKeywords;
import store.util.md.MdReader;

public class FreeProductsCheckerTest {

    Products products = new Products();
    Promotions promotions = new Promotions();
    MdReader reader = new MdReader();
    PromotionTimer timer = new PromotionTimer(products, promotions);
    FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 증정품_기능_테스트_1() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-05-05T23:59:59"));

        List<ReceiptFreeProductDTO> freeProducts = freeProductsChecker.createFreeProductDTOs(orders);
        assertThat(freeProducts.get(0).getName()).isEqualTo("초코바");
        assertThat(freeProducts.get(0).getQuantity()).isEqualTo(2);
        assertThat(freeProducts.get(1).getName()).isEqualTo("사이다");
        assertThat(freeProducts.get(1).getQuantity()).isEqualTo(2);
        assertFalse(freeProducts.stream().anyMatch(p -> p.getName().equals("감자칩")));
    }

    @Test
    void 증정품_기능_테스트_2() {
        Orders orders = new Orders("[초코바-3],[감자칩-3],[초코바-3],[사이다-8]", products);

        timer.setTime(LocalDateTime.parse("2024-11-05T23:59:59"));

        List<ReceiptFreeProductDTO> freeProducts = freeProductsChecker.createFreeProductDTOs(orders);
        assertThat(freeProducts.get(0).getName()).isEqualTo("초코바");
        assertThat(freeProducts.get(0).getQuantity()).isEqualTo(2);
        assertThat(freeProducts.get(1).getName()).isEqualTo("감자칩");
        assertThat(freeProducts.get(1).getQuantity()).isEqualTo(1);
        assertThat(freeProducts.get(2).getName()).isEqualTo("사이다");
        assertThat(freeProducts.get(2).getQuantity()).isEqualTo(2);
    }
}
