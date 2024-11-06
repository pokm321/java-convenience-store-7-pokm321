package store.domain;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Products implements MdData<Product>{

    private List<Product> listOfProducts = new ArrayList<>();

    public List<Product> getAll() {
        return listOfProducts;
    }

    @Override
    public void addItem(String line) {
        listOfProducts.add(new Product(line));
    }
}
