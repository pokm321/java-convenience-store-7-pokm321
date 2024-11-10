package store.domain;

import java.util.List;
import store.util.md.MdErrors;

public class Product {

    private static final int FIELD_COUNT = 4;
    private static final String DELIMITER = ",";

    private final String name;
    private final long price;
    private int quantity;
    private final String promotion;

    public Product(String line) {
        validate(line);

        List<String> fields = getFields(line);
        this.name = fields.get(0);
        this.price = Long.parseLong(fields.get(1));
        this.quantity = Integer.parseInt(fields.get(2));
        this.promotion = fields.get(3);
    }

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER));
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }


    private void validate(String line) {
        validateNotNull(line);
        validateNotEmpty(line);
        List<String> fields = getFields(line);
        validateCount(fields);
        validateInteger(fields);
    }

    private void validateNotNull(String line) {
        if (line == null) {
            throw new IllegalArgumentException(MdErrors.MD_EMPTY_ERROR.getMessage());
        }
    }

    private void validateNotEmpty(String line) {
        if (line.isBlank()) {
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
}
