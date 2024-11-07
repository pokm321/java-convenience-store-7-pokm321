package store.domain.input;

import java.util.List;

public class Order {

    private static final char LEFT_BRACKET = '[';
    private static final char RIGHT_BRACKET = ']';
    private static final String HYPHEN = "-";
    private static final int FIELD_COUNT = 2;
    private static final int MIN_QUANTITY = 0;

    private final String name;
    private final int quantity;

    public Order(String rawOrder) {
        validate(rawOrder);

        List<String> fields = getFields(rawOrder);
        this.name = fields.get(0);
        this.quantity = Integer.parseInt(fields.get(1));
    }

    private List<String> getFields(String rawOrder) {
        rawOrder = rawOrder.substring(1, rawOrder.length() - 1);
        return List.of(rawOrder.split(HYPHEN, -1));
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    private void validate(String rawOrder) {
        validateNotNull(rawOrder);
        validateNotEmpty(rawOrder);
        validateBrackets(rawOrder);

        List<String> fields = getFields(rawOrder);
        validateNotEmpty(fields);
        validateCount(fields);
        validateInteger(fields);
        validateRange(fields);
    }

    private void validateNotNull(String rawOrder) {
        if (rawOrder == null) {
            throw new IllegalArgumentException(InputErrors.OTHERS.getMessage());
        }
    }

    private void validateNotEmpty(String rawOrder) {
        if (rawOrder.isBlank()) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateBrackets(String rawOrder) {
        if (rawOrder.charAt(0) != LEFT_BRACKET || rawOrder.charAt(rawOrder.length() - 1) != RIGHT_BRACKET) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateNotEmpty(List<String> fields) {
        if (fields.stream().anyMatch(String::isBlank)) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateCount(List<String> fields) {
        if (fields.size() != FIELD_COUNT) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateInteger(List<String> fields) {
        try {
            Integer.parseInt(fields.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }

    private void validateRange(List<String> fields) {
        if (Integer.parseInt(fields.get(1)) <= MIN_QUANTITY) {
            throw new IllegalArgumentException(InputErrors.INVALID_FORMAT.getMessage());
        }
    }
}
