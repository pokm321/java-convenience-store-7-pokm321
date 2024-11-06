package store.domain;

import java.util.List;

public class Promotion {
    public static final String DELIMITER = ",";

    private final String name;
    private final int buy;
    private final int get;
    private final String startDate;
    private final String endDate;

    public Promotion(String line) {
        List<String> fields = getFields(line);

        this.name = fields.get(0);
        this.buy = Integer.parseInt(fields.get(1));
        this.get = Integer.parseInt(fields.get(2));
        this.startDate = fields.get(3);
        this.endDate = fields.get(4);
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

    private List<String> getFields(String line) {
        return List.of(line.split(DELIMITER));
    }
}
