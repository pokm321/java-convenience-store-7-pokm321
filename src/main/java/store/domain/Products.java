package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;
import store.util.md.MdKeywords;
import store.view.InputErrors;

public class Products {

    private final List<Product> listOfProducts = new ArrayList<>();

    public Products(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            addItems(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }
    }

    public List<Product> getAll() {
        return listOfProducts;
    }

    public String getPromotionNameByName(String name) {
        List<Product> promotedProducts = getPromotedProductsByName(name);

        if (promotedProducts.isEmpty()) {
            return MdKeywords.NULL.getValue();
        }

        return promotedProducts.getFirst().getPromotion();
    }

    public List<Product> getProductsByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name)).toList();
    }

    public List<Product> getNullProductsByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name))
                .filter(p -> p.getPromotion().equals(MdKeywords.NULL.getValue())).toList();
    }

    public List<Product> getPromotedProductsByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name))
                .filter(p -> !p.getPromotion().equals(MdKeywords.NULL.getValue())).toList();
    }

    public int getQuantityByName(String name) {
        return getProductsByName(name).stream().mapToInt(Product::getQuantity).sum();
    }

    public int getPromotedQuantityByName(String name) {
        return getPromotedProductsByName(name).stream().mapToInt(Product::getQuantity).sum();
    }

    public int getPriceByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(InputErrors.INVALID_NAME.getMessage()))
                .getPrice();
    }

    private void addItems(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            listOfProducts.add(new Product(line));
        }
    }
}
