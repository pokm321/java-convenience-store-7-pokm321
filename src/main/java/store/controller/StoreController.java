package store.controller;

import store.domain.Products;
import store.domain.Promotions;
import store.util.MdReader;

public class StoreController {

    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";

    public void run() {
        MdReader productsReader = new MdReader(productsPath);
        Products products = getProducts(productsReader);

        MdReader promotionsReader = new MdReader(promotionsPath);
        Promotions promotions = getPromotions(promotionsReader);

    }

    private Products getProducts(MdReader reader) {
        Products products = new Products();
        String line;

        reader.readLine();
        while ((line = reader.readLine()) != null) {
            products.addProduct(line);
        }

        return products;
    }

    private Promotions getPromotions(MdReader reader) {
        Promotions promotions = new Promotions();
        String line;

        reader.readLine();
        while ((line = reader.readLine()) != null) {
            promotions.addPromotion(line);
        }

        return promotions;
    }
}
