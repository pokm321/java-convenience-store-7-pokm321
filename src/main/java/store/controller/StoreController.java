package store.controller;

import store.domain.Products;
import store.util.MdReader;

public class StoreController {

    private static final String productsPath = "src/main/resources/products.md";
    private static final String promotionsPath = "src/main/resources/promotions.md";

    public void run() {
        MdReader productsReader = new MdReader(productsPath);
        Products products = getProducts(productsReader);

        MdReader promotionsReader = new MdReader(promotionsPath);


    }

    private Products getProducts(MdReader reader) {
        Products products = new Products();
        String line;
        while ((line = reader.readLine()) != null) {
            products.addProduct(line);
        }

        return products;
    }
}
