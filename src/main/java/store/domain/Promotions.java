package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Promotions implements MdData<Promotion>{

    private List<Promotion> listOfPromotions = new ArrayList<>();

    public List<Promotion> getAll() {
        return listOfPromotions;
    }

    @Override
    public void addItem(String line) {
        listOfPromotions.add(new Promotion(line));
    }
}
