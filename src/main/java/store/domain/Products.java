package store.domain;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class Products {

    private List<Product> listOfProducts = new ArrayList<>();

    public List<Product> getAll() {
        return listOfProducts;
    }

    public void addProduct(String name, int price, int quantity, String promotion) {
        listOfProducts.add(new Product(name, price, quantity, promotion));
    }
}
