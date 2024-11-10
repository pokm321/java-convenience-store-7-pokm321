package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;
import store.util.md.MdKeywords;
import store.view.ViewErrors;

public class Products {

    private static final int INDEX_NAME = 0;
    private static final int INDEX_PRICE = 1;
    private static final int INDEX_QUANTITY = 2;
    private static final int INDEX_PROMOTION = 3;
    private static final int FIELD_COUNT = 4;
    private static final String DELIMITER = ",";

    private final List<Product> listOfProducts = new ArrayList<>();

    public void addProduct(String line) {
        validate(line);

        List<String> fields = getFields(line);

        if (tryMergingExistingProduct(fields)) {
            return;
        }

        addNewProduct(fields);
    }

    private boolean tryMergingExistingProduct(List<String> fields) {
        if (!getProductsByFields(fields).isEmpty()) {
            Product product = getProductsByFields(fields).getFirst();
            product.setQuantity(product.getQuantity() + Integer.parseInt(fields.get(INDEX_QUANTITY)));
            return true;
        }
        return false;
    }

    private List<Product> getProductsByFields(List<String> fields) {
        return listOfProducts.stream()
                .filter(product -> product.getName().equals(fields.get(INDEX_NAME)))
                .filter(product -> product.getPromotion().equals(fields.get(INDEX_PROMOTION))).toList();
    }

    private void addNewProduct(List<String> fields) {
        listOfProducts.add(new Product(
                fields.get(INDEX_NAME),
                Long.parseLong(fields.get(INDEX_PRICE)),
                Integer.parseInt(fields.get(INDEX_QUANTITY)),
                fields.get(INDEX_PROMOTION)));

        if (isPromotionedProduct(fields)) {
            addNormalProductWithZeroQuantity(fields);
        }
    }

    private boolean isPromotionedProduct(List<String> fields) {
        return !fields.get(INDEX_PROMOTION).equals(MdKeywords.NULL.getValue());
    }

    private void addNormalProductWithZeroQuantity(List<String> fields) {
        listOfProducts.add(new Product(
                fields.get(INDEX_NAME),
                Long.parseLong(fields.get(INDEX_PRICE)),
                0,
                MdKeywords.NULL.getValue()));
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
            Long.parseLong(fields.get(INDEX_PRICE));
            Integer.parseInt(fields.get(INDEX_QUANTITY));
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
