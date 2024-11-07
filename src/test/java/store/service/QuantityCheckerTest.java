package store.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import store.domain.MdPaths;
import store.domain.Products;
import store.domain.input.Orders;

public class QuantityCheckerTest {

    @Test
    void 재고_확인_테스트() {
        Products products = new Products(MdPaths.PRODUCTS.getPath());

        Orders orders = new Orders("[초코바-10],[사이다-16]");
        QuantityChecker checker = new QuantityChecker(products, orders);
        assertFalse(checker.isEnoughQuantity());

        orders = new Orders("[초코바-10],[사이다-15]");
        checker = new QuantityChecker(products, orders);
        assertTrue(checker.isEnoughQuantity());
    }
}
