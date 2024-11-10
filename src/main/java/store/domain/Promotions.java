package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;

public class Promotions {

    private static final int INDEX_NAME = 0;
    private static final int INDEX_BUY = 1;
    private static final int INDEX_GET = 2;
    private static final int INDEX_START_DATE = 3;
    private static final int INDEX_END_DATE = 4;
    private static final int FIELD_COUNT = 5;
    private static final String DELIMITER = ",";

    private final List<Promotion> listOfPromotions = new ArrayList<>();

    public void addPromotion(String line) {
        validate(line);

        List<String> fields = getFields(line);
        listOfPromotions.add(new Promotion(
                fields.get(INDEX_NAME),
                Integer.parseInt(fields.get(INDEX_BUY)),
                Integer.parseInt(fields.get(INDEX_GET)),
                fields.get(INDEX_START_DATE),
                fields.get(INDEX_END_DATE)
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
            Integer.parseInt(fields.get(INDEX_BUY));
            Integer.parseInt(fields.get(INDEX_GET));
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
