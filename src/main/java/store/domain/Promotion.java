package store.domain;

import java.util.List;

public class Promotion {

    private static final int FIELD_COUNT = 5;
    private static final String DELIMITER = ",";

    private final String name;
    private final int buy;
    private final int get;
    private final String startDate;
    private final String endDate;

    public Promotion(String line) {
        validate(line);

        List<String> fields = getFields(line);
        this.name = fields.get(0);
        this.buy = Integer.parseInt(fields.get(1));
        this.get = Integer.parseInt(fields.get(2));
        this.startDate = fields.get(3);
        this.endDate = fields.get(4);
    }

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER));
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
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
            Integer.parseInt(fields.get(1));
            Integer.parseInt(fields.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MdErrors.MD_INTEGER_ERROR.getMessage());
        }
    }
}
