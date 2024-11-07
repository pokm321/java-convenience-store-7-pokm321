package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private List<Promotion> listOfPromotions = new ArrayList<>();

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

    private void addItems(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            listOfPromotions.add(new Promotion(line));
        }
    }
}
