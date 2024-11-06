package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private List<Promotion> listOfPromotions = new ArrayList<>();

    public List<Promotion> getAll() {
        return listOfPromotions;
    }

    public void addPromotion(String line) {
        listOfPromotions.add(new Promotion(line));
    }
}
