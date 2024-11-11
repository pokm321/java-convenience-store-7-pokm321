package store.service.stockmanager;

import java.util.List;
import store.domain.Products;
import store.domain.input.Orders;
import store.dto.receipt.ProductDTO;

public class OrdersChecker {

    private final Products products;

    public OrdersChecker(Products products) {
        this.products = products;
    }

    public List<ProductDTO> createProductDTOs(Orders orders) {
        return orders.getAll().stream().map(order ->
                        new ProductDTO(order.getName(),
                                order.getQuantity(),
                                order.getQuantity() * products.getPriceByName(order.getName())))
                .toList();
    }
}
