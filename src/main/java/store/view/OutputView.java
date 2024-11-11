package store.view;

import java.io.UnsupportedEncodingException;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.dto.receipt.FooterDTO;
import store.dto.receipt.FreeProductDTO;
import store.dto.receipt.ProductDTO;
import store.util.md.MdKeywords;

public class OutputView {

    private static final String KOREAN_ENCODING = "euc-kr";

    public void printError(String message) {
        System.out.println(message);
    }

    public void printStock(Products products) {
        System.out.printf(ViewMessages.WELCOME.getMessage());

        for (Product product : products.getAll()) {
            System.out.printf(ViewMessages.ITEM_INFO.getMessage(), product.getName(), product.getPrice());

            printQuantity(product.getQuantity());
            printPromotion(product.getPromotion());
        }
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

    public void printReceipt(List<ProductDTO> productDTOs, List<FreeProductDTO> freeProductDTOs, FooterDTO footerDTO) {
        printProducts(productDTOs);
        printFreeProducts(freeProductDTOs);
        printFooter(footerDTO);
    }

    private void printProducts(List<ProductDTO> productDTOs) {
        System.out.println(Receipt.HEADER_RECEIPT.getText());
        System.out.println(Receipt.HEADER_PRODUCTS.getText());
        productDTOs.forEach(product -> System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseToLength(product.getName(), Receipt.FIRST_COLUMN_LENGTH.getNumber()), product.getQuantity(),
                product.getTotalPrice()));
    }

    private void printFreeProducts(List<FreeProductDTO> freeProducts) {
        System.out.println(Receipt.HEADER_FREE_PRODUCTS.getText());
        freeProducts.forEach(product -> System.out.printf(Receipt.COLUMN_FORMAT_FIRST_SECOND.getText(),
                parseToLength(product.getName(), Receipt.FIRST_COLUMN_LENGTH.getNumber()), product.getQuantity()));
    }

    private void printFooter(FooterDTO footerDTO) {
        System.out.println(Receipt.FOOTER_RECEIPT.getText());
        printRawTotal(footerDTO);
        printPromotionDiscount(footerDTO.getPromotionDiscount());
        printMembershipDiscount(footerDTO.getMembershipDiscount());
        printPayment(footerDTO.getPayment());
    }

    private void printRawTotal(FooterDTO footerDTO) {
        System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseToLength(Receipt.ROW_TOTAL_PRICE.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                footerDTO.getTotalQuantity(), footerDTO.getRawTotalPrice());
    }

    private void printPromotionDiscount(long promotionDiscount) {
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseToLength(Receipt.ROW_PROMOTION_DISCOUNT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                promotionDiscount);
    }

    private void printMembershipDiscount(long membershipDiscount) {
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseToLength(Receipt.ROW_MEMBERSHIP_DISCOUNT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                membershipDiscount);
    }

    private void printPayment(long payment) {
        System.out.printf(Receipt.COLUMN_FORMAT_FIRST_THIRD.getText(),
                parseToLength(Receipt.ROW_PAYMENT.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                payment);
    }

    private String parseToLength(String rawText, int desiredLength) {
        StringBuilder text = new StringBuilder(rawText);
        while (getTextBytes(text) > desiredLength) {
            text.deleteCharAt(text.length() - 1);
        }

        while (getTextBytes(text) < desiredLength) {
            text.append(ViewMessages.SPACE.getMessage());
        }

        return text.toString();
    }

    private int getTextBytes(StringBuilder text) {
        try {
            return text.toString().getBytes(KOREAN_ENCODING).length;
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(ViewErrors.INVALID_ENCODING.getMessage());
        }
    }


}


