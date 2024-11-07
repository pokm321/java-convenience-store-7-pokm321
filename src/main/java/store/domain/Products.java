package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Products {

    private List<Product> listOfProducts = new ArrayList<>();

    public Products(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            addItems(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }
    }

    public List<Product> getAll() {
        return listOfProducts;
    }

    private void addItems(BufferedReader reader) throws IOException {
        String line;
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            listOfProducts.add(new Product(line));
        }
    }
}
