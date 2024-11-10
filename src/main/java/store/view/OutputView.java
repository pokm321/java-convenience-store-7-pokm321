package store.view;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.input.Order;
import store.util.md.MdKeywords;

public class OutputView {

    private static final String KOREAN_ENCODING = "euc-kr";

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
        if (quantity == MdKeywords.NO_QUANTITY.getNumber()) {
            System.out.print(ViewMessages.NO_QUANTITY_OUTPUT.getMessage());
            return;
        }
        System.out.printf(ViewMessages.QUANTITY.getMessage(), quantity);
    }

    private void printPromotion(String promotion) {
        if (promotion.equals(MdKeywords.NULL.getText())) {
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
        System.out.println(Receipt.HEADER_RECEIPT.getText());
        System.out.println(Receipt.HEADER_PRODUCTS.getText());
        orders.forEach(o -> System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseKoreanToLength(o.getName(), Receipt.FIRST_COLUMN_LENGTH.getNumber()), o.getQuantity(),
                o.getQuantity() * products.getPriceByName(o.getName())));
    }

    private void printFreeProducts(Map<String, Integer> freeProducts) {
        System.out.println(Receipt.HEADER_FREE_PRODUCTS.getText());
        freeProducts.forEach((key, value) -> System.out.printf(Receipt.COLUMN_FORMAT_FIRST_SECOND.getText(),
                parseKoreanToLength(key, Receipt.FIRST_COLUMN_LENGTH.getNumber()), value));
    }

    private void printFooter(List<Order> orders, long rawTotal, long promotionDiscount, long membershipDiscount) {
        System.out.println(Receipt.FOOTER_RECEIPT.getText());
        System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseKoreanToLength(Receipt.ROW_TOTAL_PRICE.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                orders.stream().mapToInt(Order::getQuantity).sum(),
                rawTotal);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseKoreanToLength(Receipt.ROW_PROMOTION_DISCOUNT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                promotionDiscount * -1);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseKoreanToLength(Receipt.ROW_MEMBERSHIP_DISCOUNT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                membershipDiscount * -1);
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseKoreanToLength(Receipt.ROW_PAYMENT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
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


