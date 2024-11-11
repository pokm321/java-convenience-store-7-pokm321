package store.service.dtocreator;

import java.util.List;
import store.domain.Products;
import store.domain.input.Orders;
import store.dto.receipt.ReceiptProductDTO;

public class OrdersChecker {

    private final Products products;

    public OrdersChecker(Products products) {
        this.products = products;
    }

    public List<ReceiptProductDTO> createReceiptProductDTOs(Orders orders) {
        return orders.getAll().stream().map(order ->
                        new ReceiptProductDTO(order.getName(),
                                order.getQuantity(),
                                order.getQuantity() * products.getPriceByName(order.getName())))
                .toList();
    }


}
