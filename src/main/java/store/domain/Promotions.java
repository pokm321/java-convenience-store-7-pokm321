package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;

public class Promotions {

    private static final int FIELD_COUNT = 5;
    private static final String DELIMITER = ",";

    private final List<Promotion> listOfPromotions = new ArrayList<>();

    public void addPromotion(String line) {
        validate(line);

        List<String> fields = getFields(line);
        listOfPromotions.add(new Promotion(
                fields.get(0),
                Integer.parseInt(fields.get(1)),
                Integer.parseInt(fields.get(2)),
                fields.get(3),
                fields.get(4)
        ));
    }

    private void validate(String line) {
        validateNotNull(line);
        validateNotEmpty(line);
        List<String> fields = getFields(line);
        validateCount(fields);
        validateInteger(fields);
    }

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER));
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
            Integer.parseInt(fields.get(1));
            Integer.parseInt(fields.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MdErrors.MD_INTEGER_ERROR.getMessage());
        }
    }

    public List<Promotion> getAll() {
        return listOfPromotions;
    }

    public Promotion getPromotion(String promotionName) {
        return listOfPromotions.stream().filter(promotion -> promotion.getName().equals(promotionName)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage()));
    }
}
