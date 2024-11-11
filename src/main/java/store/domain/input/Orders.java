package store.domain.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import store.domain.Products;
import store.view.ViewErrors;

public class Orders {

    private static final String COMMA = ",";
    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';
    private static final String HYPHEN = "-";

    private final List<Order> listOfOrders = new ArrayList<>();

    public Orders(String rawOrders, Products products) {
        validate(rawOrders);

        getMergedOrders(rawOrders).forEach((name, quantity) ->
                listOfOrders.add(new Order(name, quantity, products)));
    }

    public List<Order> getAll() {
        return listOfOrders;
    }

    public int getTotalQuantity() {
        return listOfOrders.stream().mapToInt(Order::getQuantity).sum();
    }

    public LinkedHashMap<String, Integer> getMergedOrders(String rawOrders) {
        return splitOrders(rawOrders).stream().map(this::getFields).collect(Collectors.toMap(fields ->
                fields.get(0), fields -> Integer.parseInt(fields.get(1)), Integer::sum, LinkedHashMap::new));
    }

    private List<String> splitOrders(String rawOrders) {
        return List.of(rawOrders.split(COMMA, -1));
    }

    private List<String> getFields(String rawOrder) {
        rawOrder = rawOrder.substring(1, rawOrder.length() - 1);
        return List.of(rawOrder.split(HYPHEN, -1));
    }

    private void validate(String rawOrders) {
        validateNotNull(rawOrders);
        validateNotEmpty(rawOrders);

        for (String order : splitOrders(rawOrders)) {
            validateBrackets(order);
            validateHyphen(order);
            validateInteger(order);
        }
    }

    private void validateNotNull(String rawOrders) {
        if (rawOrders == null) {
            throw new IllegalArgumentException(ViewErrors.OTHERS.getMessage());
        }
    }

    private void validateNotEmpty(String rawOrders) {
        if (rawOrders.isBlank()) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }

        if (Arrays.stream(rawOrders.split(COMMA)).anyMatch(String::isBlank)) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateBrackets(String order) {
        if (order.charAt(0) != LEFT_BRACKET || order.charAt(order.length() - 1) != RIGHT_BRACKET) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }

    }

    private void validateHyphen(String order) {
        if (order.replace(HYPHEN, "").length() + 1 != order.length()) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateInteger(String order) {
        try {

            Integer.parseInt(getFields(order).get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ViewErrors.INVALID_FORMAT.getMessage());
        }
    }
}
