package store.controller;

import store.domain.Products;
import store.domain.Promotions;
import store.service.MdReader;

public class StoreController {

    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";

    public void run() {
        MdReader reader = new MdReader(productsPath);
        Products products = reader.addItems(new Products());

        reader = new MdReader(promotionsPath);
        Promotions promotions = reader.addItems(new Promotions());

    }
}
