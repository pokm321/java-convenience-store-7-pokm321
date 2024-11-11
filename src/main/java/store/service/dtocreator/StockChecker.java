package store.service.dtocreator;

import java.util.List;
import store.domain.Products;
import store.dto.stock.StockProductDTO;

public class StockChecker {

    private final Products products;

    public StockChecker(Products products) {
        this.products = products;
    }

    public List<StockProductDTO> createStockProductDTOs() {
        return products.getAll().stream().map(product ->
                new StockProductDTO(product.getName(), product.getPrice(),
                        product.getQuantity(), product.getPromotion()
                )).toList();
    }
}
