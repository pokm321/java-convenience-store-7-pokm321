package store.domain.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import store.domain.Products;
import store.view.InputErrors;

public class Orders {

    private static final String COMMA = ",";
    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';
    private static final String HYPHEN = "-";
    private static final int MIN_QUANTITY = 0;

    private List<Order> listOfOrders = new ArrayList<>();

    public Orders(String rawOrders, Products products) {
        validateParsing(rawOrders);

        for (Entry<String, Integer> order : parseOrders(rawOrders).entrySet()) {
            listOfOrders.add(new Order(order.getKey(), order.getValue()));
        }

        validateWithStock(products);
    }

    public List<Order> getAll() {
        return listOfOrders;
    }

    public Map<String, Integer> parseOrders(String rawOrders) {
        Map<String, Integer> mergedOrders = new HashMap<>();

        splitOrders(rawOrders).forEach(order -> {
            List<String> fields = getFields(order);
            mergedOrders.merge(fields.get(0), Integer.parseInt(fields.get(1)), Integer::sum);
        });
        return mergedOrders;
    }

    private List<String> splitOrders(String rawOrders) {
        return List.of(rawOrders.split(COMMA, -1));
    }

    private List<String> getFields(String rawOrder) {
        rawOrder = rawOrder.substring(1, rawOrder.length() - 1);
        return List.of(rawOrder.split(HYPHEN, -1));
    }

    private void validateParsing(String rawOrders) {
        validateNotNull(rawOrders);
        validateNotEmpty(rawOrders);

        for (String order : splitOrders(rawOrders)) {
            validateBrackets(order);
            validateHyphen(order);
            validateInteger(order);
            validateRange(order);
        }
    }

    private void validateNotNull(String rawOrders) {
        if (rawOrders == null) {
            throw new IllegalArgumentException(InputErrors.OTHERS.getMessage());
        }
    }

    private void validateNotEmpty(String rawOrders) {
        if (rawOrders.isBlank()) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }

        if (Arrays.stream(rawOrders.split(COMMA)).anyMatch(String::isBlank)) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateBrackets(String order) {
        if (order.charAt(0) != LEFT_BRACKET || order.charAt(order.length() - 1) != RIGHT_BRACKET) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }

    }

    private void validateHyphen(String order) {
        if (order.replace(HYPHEN, "").length() + 1 != order.length()) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateInteger(String order) {
        try {

            Integer.parseInt(getFields(order).get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateRange(String order) {
        if (Integer.parseInt(getFields(order).get(1)) <= MIN_QUANTITY) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateWithStock(Products products) {
        validateNameExists(products);
        validateQuantityEnough(products);
    }

    private void validateNameExists(Products products) {
        if (listOfOrders.stream().anyMatch(order -> products.getProductsByName(order.getName()).isEmpty())) {
            throw new IllegalArgumentException(InputErrors.INVALID_NAME.getMessage());
        }
    }

    private void validateQuantityEnough(Products products) {
        Map<String, Integer> productsMerged = products.mergeProducts();
        if (listOfOrders.stream().anyMatch(order -> productsMerged.get(order.getName()) < order.getQuantity())) {
            throw new IllegalArgumentException(InputErrors.INVALID_QUANTITY.getMessage());
        }
    }

}
