package store.util.md;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import store.domain.Products;
import store.domain.Promotions;

public class MdReader {

    public void readProducts(Products products, String path) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                products.addProduct(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }
    }

    public void readPromotions(Promotions promotions, String path) {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                promotions.addPromotion(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(MdErrors.MD_READ_FAIL.getMessage());
        }
    }
}
