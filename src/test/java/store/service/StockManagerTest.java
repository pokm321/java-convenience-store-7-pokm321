package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.input.Orders;
import store.util.md.MdPaths;

public class StockManagerTest {

    Products products = new Products(MdPaths.PRODUCTS.getPath());

//    @Test
//    void 재고_감소_기능_테스트() {
//        Order order = new Order("[초코바-7]");
//        assertThat(products.getProductsByName("초코바").stream()
//                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);
//
//        StockManager manager = new StockManager(products);
//        manager.deductOrder(order, false);
//
//        assertThat(products.getProductsByName("초코바").stream()
//                .filter(p -> p.getPromotion().equals("null")).toList()
//                .get(0).getQuantity()).isEqualTo(0);
//
//        assertThat(products.getProductsByName("초코바").stream()
//                .filter(p -> !p.getPromotion().equals("null")).toList()
//                .get(0).getQuantity()).isEqualTo(3);
//    }
//
//    @Test
//    void 프로모션중_재고_감소_기능_테스트() {
//        Order order = new Order("[초코바-7]");
//        assertThat(products.getProductsByName("초코바").stream()
//                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);
//
//        StockManager manager = new StockManager(products);
//        manager.deductOrder(order, true);
//
//        assertThat(products.getProductsByName("초코바").stream()
//                .filter(p -> p.getPromotion().equals("null")).toList()
//                .get(0).getQuantity()).isEqualTo(3);
//
//        assertThat(products.getProductsByName("초코바").stream()
//                .filter(p -> !p.getPromotion().equals("null")).toList()
//                .get(0).getQuantity()).isEqualTo(0);
//    }

    @Test
    void 여러_주문_기능_테스트() {
        Orders orders = new Orders("[초코바-3],[사이다-3],[초코바-2]");
        assertThat(products.getProductsByName("초코바").stream()
                .mapToInt(Product::getQuantity).sum()).isEqualTo(10);

        StockManager manager = new StockManager(products);
        manager.deductOrders(orders, true);

        assertThat(products.getProductsByName("초코바").stream()
                .filter(p -> p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(5);

        assertThat(products.getProductsByName("초코바").stream()
                .filter(p -> !p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(0);

        assertThat(products.getProductsByName("사이다").stream()
                .filter(p -> !p.getPromotion().equals("null")).toList()
                .get(0).getQuantity()).isEqualTo(5);
    }
}
