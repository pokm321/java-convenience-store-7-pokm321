package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Orders;
import store.dto.receipt.FooterDTO;
import store.dto.receipt.FreeProductDTO;
import store.service.stockmanager.FreeProductsChecker;
import store.util.Retrier;
import store.util.md.MdKeywords;
import store.util.md.MdReader;
import store.view.InputView;

public class PriceCalculatorTest {

    InputView inputView = new InputView();
    MdReader reader = new MdReader();
    Products products = new Products();
    Promotions promotions = new Promotions();
    Retrier retrier = new Retrier();
    PromotionTimer timer = new PromotionTimer(products, promotions);
    FreeProductsChecker freeProductsChecker = new FreeProductsChecker(products, promotions, timer);
    PriceCalculator calculator = new PriceCalculator(inputView, products, promotions, timer, retrier);

    @BeforeEach
    void setup() {
        reader.readProducts(products, MdKeywords.PRODUCTS_PATH.getText());
        reader.readPromotions(promotions, MdKeywords.PROMOTIONS_PATH.getText());
    }

    @Test
    void 시간대별_계산_테스트_1() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);

        Console.close();
        System.setIn(new ByteArrayInputStream("Y\nY\nY\n".getBytes()));
        timer.setTime(LocalDateTime.parse("2023-05-08T01:20:30"));
        List<FreeProductDTO> freeProducts = freeProductsChecker.createFreeProductDTOs(orders);
        FooterDTO footerDTO = calculator.createFooterDTO(orders, freeProducts);
        assertThat(footerDTO.getRawTotalPrice()).isEqualTo(24000);
        assertThat(footerDTO.getPromotionDiscount()).isEqualTo(0);
        assertThat(footerDTO.getMembershipDiscount()).isEqualTo(-7200);
        assertThat(footerDTO.getPayment()).isEqualTo(16800);
    }

    @Test
    void 시간대별_계산_테스트_2() {
        Orders orders = new Orders("[초코바-10],[사이다-9],[감자칩-2]", products);

        Console.close();
        System.setIn(new ByteArrayInputStream("Y\nY\nY\n".getBytes()));
        timer.setTime(LocalDateTime.parse("2024-05-08T01:20:30"));
        List<FreeProductDTO> freeProducts = freeProductsChecker.createFreeProductDTOs(orders);
        FooterDTO footerDTO = calculator.createFooterDTO(orders, freeProducts);
        assertThat(footerDTO.getRawTotalPrice()).isEqualTo(24000);
        assertThat(footerDTO.getPromotionDiscount()).isEqualTo(-4400);
        assertThat(footerDTO.getMembershipDiscount()).isEqualTo(-3960);
        assertThat(footerDTO.getPayment()).isEqualTo(15640);

    }
}
