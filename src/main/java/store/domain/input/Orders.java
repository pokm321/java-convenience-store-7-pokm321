package store.domain.input;

import java.util.ArrayList;
import java.util.List;

public class Orders {

    private static final String COMMA = ",";

    private List<Order> listOfOrders = new ArrayList<>();

    Orders(String rawOrders) {
        validate(rawOrders);

        for (String rawOrder : rawOrders.split(COMMA)) {
            listOfOrders.add(new Order(rawOrder));
        }
    }

    public List<Order> getAll() {
        return listOfOrders;
    }

    private void validate(String rawOrders) {
        validateNotNull(rawOrders);
        validateNotEmpty(rawOrders);
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
    }

}
