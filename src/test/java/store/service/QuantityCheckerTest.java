package store.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import store.domain.MdPaths;
import store.domain.Products;
import store.domain.input.Orders;

public class QuantityCheckerTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());

    @Test
    void 재고_확인_테스트() {
        Orders orders = new Orders("[초코바-10],[사이다-15]");
        QuantityChecker checker = new QuantityChecker(products);
        checker.checkEnoughQuantity(orders);
    }

    @Test
    void 재고_없음_예외_테스트() {
        assertThatThrownBy(() -> {
            Orders orders = new Orders("[초코바-10],[사이다-16]");
            QuantityChecker checker = new QuantityChecker(products);
            checker.checkEnoughQuantity(orders);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
