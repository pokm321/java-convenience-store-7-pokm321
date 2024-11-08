package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.input.InputErrors;
import store.util.md.MdErrors;
import store.util.md.MdKeywords;

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

    public String getPromotionName(String name) {
        List<Product> productsOnPromotion = getProductsByName(name).stream()
                .filter(p -> !p.getPromotion().equals(MdKeywords.NULL.getValue())).toList();

        if (productsOnPromotion.isEmpty()) {
            return MdKeywords.NULL.getValue();
        }

        return productsOnPromotion.getFirst().getPromotion();
    }

    public List<Product> getProductsByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name)).toList();
    }

    public int getPriceByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(InputErrors.INVALID_NAME.getMessage()))
                .getPrice();
    }

    public Map<String, Integer> mergeProducts() {
        Map<String, Integer> mergedProducts = new HashMap<>();
        listOfProducts.forEach(p -> mergedProducts.merge(p.getName(), p.getQuantity(), Integer::sum));
        return mergedProducts;
    }

    private void addItems(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            listOfProducts.add(new Product(line));
        }
    }
}
