package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.util.md.MdErrors;

public class Promotions {

    private final List<Promotion> listOfPromotions = new ArrayList<>();

    public Promotions(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            addItems(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }
    }

    public List<Promotion> getAll() {
        return listOfPromotions;
    }

    public Promotion getPromotionByName(String name) {
        return listOfPromotions.stream().filter(promotion -> promotion.getName().equals(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage()));
    }

    private void addItems(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            listOfPromotions.add(new Promotion(line));
        }
    }
}
