package store.view;

import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;

public class OutputView {

    private static final String NO_PROMOTION_MD = "null";
    private static final int NO_QUANTITY_MD = 0;

    public void printStock(Products products, Promotions promotions) {
        System.out.printf(Outputs.WELCOME.getMessage());

        for (Product product : products.getAll()) {
            System.out.printf(Outputs.ITEM_INFO.getMessage(),
                    product.getName(), product.getPrice());

            printQuantity(product.getQuantity());
            printPromotion(product.getPromotion());
        }

        System.out.println();
    }

    private void printQuantity(int quantity) {
        if (quantity == NO_QUANTITY_MD) {
            System.out.print(Outputs.NO_QUANTITY_OUTPUT.getMessage());
            return;
        }
        System.out.printf(Outputs.QUANTITY.getMessage(), quantity);
    }

    private void printPromotion(String promotion) {
        if (promotion.equals(NO_PROMOTION_MD)) {
            System.out.println();
            return;
        }
        System.out.println(Outputs.SPACE.getMessage() + promotion);
    }

    public void printProducts() {
        System.out.println("- 콜라 1,000원 10개 탄산2+1");
        // ...
    }
}
