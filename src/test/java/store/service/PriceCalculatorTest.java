package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.input.Orders;
import store.util.md.MdPaths;

public class PriceCalculatorTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());

    @Test
    void 가격_계산_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-9]");
        PriceCalculator calculator = new PriceCalculator(products);
        assertThat(calculator.getRawTotalPrice(orders)).isEqualTo(21000L);
    }
}
