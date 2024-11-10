package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;
import store.util.md.MdKeywords;
import store.view.ViewErrors;

public class Products {

    private static final int FIELD_COUNT = 4;
    private static final String DELIMITER = ",";

    private final List<Product> listOfProducts = new ArrayList<>();

    public void addProduct(String line) {
        validate(line);

        List<String> fields = getFields(line);
        listOfProducts.add(new Product(
                fields.get(0),
                Long.parseLong(fields.get(1)),
                Integer.parseInt(fields.get(2)),
                fields.get(3)
        ));
    }

    private void validate(String line) {
        validateNotNull(line);
        List<String> fields = getFields(line);
        validateNotEmpty(fields);
        validateCount(fields);
        validateInteger(fields);
    }

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER, -1));
    }


    private void validateNotNull(String line) {
        if (line == null) {
            throw new IllegalArgumentException(MdErrors.MD_EMPTY_ERROR.getMessage());
        }
    }

    private void validateNotEmpty(List<String> fields) {
        if (fields.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException(MdErrors.MD_EMPTY_ERROR.getMessage());
        }
    }

    private void validateCount(List<String> fields) {
        if (fields.size() != FIELD_COUNT) {
            throw new IllegalArgumentException(MdErrors.MD_COUNT_ERROR.getMessage());
        }
    }

    private void validateInteger(List<String> fields) {
        try {
            Long.parseLong(fields.get(1));
            Integer.parseInt(fields.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MdErrors.MD_INTEGER_ERROR.getMessage());
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

    public long getPriceByName(String name) {
        return listOfProducts.stream().filter(product -> product.getName().equals(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(ViewErrors.INVALID_NAME.getMessage()))
                .getPrice();
    }
}
