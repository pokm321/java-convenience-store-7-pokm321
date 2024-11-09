package store.view;

import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;

public class OutputView {

    private static final String NO_PROMOTION_MD = "null";
    private static final int NO_QUANTITY_MD = 0;

    public void printError(String message) {
        System.out.println(message);
    }

    public void printStock(Products products, Promotions promotions) {
        System.out.printf(ViewMessages.WELCOME.getMessage());

        for (Product product : products.getAll()) {
            System.out.printf(ViewMessages.ITEM_INFO.getMessage(),
                    product.getName(), product.getPrice());

            printQuantity(product.getQuantity());
            printPromotion(product.getPromotion());
        }

        System.out.println();
    }

    private void printQuantity(int quantity) {
        if (quantity == NO_QUANTITY_MD) {
            System.out.print(ViewMessages.NO_QUANTITY_OUTPUT.getMessage());
            return;
        }
        System.out.printf(ViewMessages.QUANTITY.getMessage(), quantity);
    }

    private void printPromotion(String promotion) {
        if (promotion.equals(NO_PROMOTION_MD)) {
            System.out.println();
            return;
        }
        System.out.println(ViewMessages.SPACE.getMessage() + promotion);
    }

    public void printReceipt(List<Order> orders, Products products, Map<String, Integer> freeProducts, long rawTotal,
                             long promotionDiscount, long membershipDiscount) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t\t수량\t금액");
        orders.forEach(o -> System.out.printf("%s\t\t\t%,d\t%,d\n", o.getName(), o.getQuantity(),
                products.getPriceByName(o.getName())));
        System.out.println("=============증      정===============");
        freeProducts.forEach((key, value) -> System.out.printf("%s\t\t\t%,d\n", key, value));
        System.out.println("====================================");
        System.out.printf("%s\t\t\t%,d\t%,d\n", "총구매액", orders.stream().mapToInt(Order::getQuantity).sum(), rawTotal);
        System.out.printf("%s\t\t\t\t-%,d\n", "행사할인", promotionDiscount);
        System.out.printf("%s\t\t\t\t-%,d\n", "멤버십할인", membershipDiscount);
        System.out.printf("%s\t\t\t\t%,d\n", "내실돈", rawTotal - promotionDiscount - membershipDiscount);
    }
}
