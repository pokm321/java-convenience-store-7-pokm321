package store.view;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;

public class OutputView {

    private static final String NO_PROMOTION_MD = "null";
    private static final int NO_QUANTITY_MD = 0;
    private static final String KOREAN_ENCODING = "euc-kr";
    private static final int FIRST_COLUMN_OF_RECEIPT_LENGTH = 20;

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
        printProducts(orders, products);
        printFreeProducts(freeProducts);
        printFooter(orders, rawTotal, promotionDiscount, membershipDiscount);
    }

    private void printProducts(List<Order> orders, Products products) {
        System.out.println(Receipt.HEADER_RECEIPT.getValue());
        System.out.println(Receipt.HEADER_PRODUCTS.getValue());
        orders.forEach(o -> System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getValue(),
                parseKoreanToLength(o.getName(), FIRST_COLUMN_OF_RECEIPT_LENGTH), o.getQuantity(),
                o.getQuantity() * products.getPriceByName(o.getName())));
    }

    private void printFreeProducts(Map<String, Integer> freeProducts) {
        System.out.println(Receipt.HEADER_FREE_PRODUCTS.getValue());
        freeProducts.forEach((key, value) -> System.out.printf(Receipt.COLUMN_FORMAT_FIRST_SECOND.getValue(),
                parseKoreanToLength(key, FIRST_COLUMN_OF_RECEIPT_LENGTH), value));
    }

    private void printFooter(List<Order> orders, long rawTotal, long promotionDiscount, long membershipDiscount) {
        System.out.println(Receipt.FOOTER_RECEIPT.getValue());
        System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getValue(),
                parseKoreanToLength(Receipt.ROW_TOTAL_PRICE.getValue(), FIRST_COLUMN_OF_RECEIPT_LENGTH),
                orders.stream().mapToInt(Order::getQuantity).sum(),
                rawTotal);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getValue(),
                parseKoreanToLength(Receipt.ROW_PROMOTION_DISCOUNT.getValue(), FIRST_COLUMN_OF_RECEIPT_LENGTH),
                promotionDiscount * -1);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getValue(),
                parseKoreanToLength(Receipt.ROW_MEMBERSHIP_DISCOUNT.getValue(), FIRST_COLUMN_OF_RECEIPT_LENGTH),
                membershipDiscount * -1);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getValue(),
                parseKoreanToLength(Receipt.ROW_PAYMENT.getValue(), FIRST_COLUMN_OF_RECEIPT_LENGTH),
                rawTotal - promotionDiscount - membershipDiscount);
    }

    private String parseKoreanToLength(String rawText, int desiredLength) {
        try {
            StringBuilder text = new StringBuilder(rawText);
            while (text.toString().getBytes(KOREAN_ENCODING).length > desiredLength) {
                text.deleteCharAt(text.length() - 1);
            }

            while (text.toString().getBytes(KOREAN_ENCODING).length < desiredLength) {
                text.append(ViewMessages.SPACE.getMessage());
            }

            return text.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(ViewErrors.INVALID_ENCODING.getMessage());
        }
    }

}


