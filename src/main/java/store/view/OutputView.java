package store.view;

import java.io.UnsupportedEncodingException;
import java.util.List;
import store.dto.receipt.ReceiptFooterDTO;
import store.dto.receipt.ReceiptFreeProductDTO;
import store.dto.receipt.ReceiptProductDTO;
import store.dto.stock.StockProductDTO;
import store.util.md.MdKeywords;

public class OutputView {

    private static final String KOREAN_ENCODING = "euc-kr";

    public void printError(String message) {
        System.out.println(message);
    }

    public void printStock(List<StockProductDTO> stockProductDTOs) {
        System.out.printf(ViewMessages.WELCOME.getMessage());

        stockProductDTOs.forEach(product -> {
            System.out.printf(ViewMessages.ITEM_INFO.getMessage(), product.getName(), product.getPrice());

            printQuantity(product.getQuantity());
            printPromotion(product.getPromotion());
        });
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

    public void printReceipt(List<ReceiptProductDTO> receiptProductDTOS,
                             List<ReceiptFreeProductDTO> receiptFreeProductDTOS, ReceiptFooterDTO receiptFooterDTO) {
        printProducts(receiptProductDTOS);
        printFreeProducts(receiptFreeProductDTOS);
        printFooter(receiptFooterDTO);
    }

    private void printProducts(List<ReceiptProductDTO> receiptProductDTOS) {
        System.out.println(Receipt.HEADER_RECEIPT.getText());
        System.out.println(Receipt.HEADER_PRODUCTS.getText());
        receiptProductDTOS.forEach(product -> System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseToLength(product.getName(), Receipt.FIRST_COLUMN_LENGTH.getNumber()), product.getQuantity(),
                product.getTotalPrice()));
    }

    private void printFreeProducts(List<ReceiptFreeProductDTO> freeProducts) {
        System.out.println(Receipt.HEADER_FREE_PRODUCTS.getText());
        freeProducts.forEach(product -> System.out.printf(Receipt.COLUMN_FORMAT_FIRST_SECOND.getText(),
                parseToLength(product.getName(), Receipt.FIRST_COLUMN_LENGTH.getNumber()), product.getQuantity()));
    }

    private void printFooter(ReceiptFooterDTO receiptFooterDTO) {
        System.out.println(Receipt.FOOTER_RECEIPT.getText());
        printRawTotal(receiptFooterDTO);
        printPromotionDiscount(receiptFooterDTO.getPromotionDiscount());
        printMembershipDiscount(receiptFooterDTO.getMembershipDiscount());
        printPayment(receiptFooterDTO.getPayment());
    }

    private void printRawTotal(ReceiptFooterDTO receiptFooterDTO) {
        System.out.printf(Receipt.COLUMN_FORMAT_ALL_THREE.getText(),
                parseToLength(Receipt.ROW_TOTAL_PRICE.getText(), Receipt.FIRST_COLUMN_LENGTH.getNumber()),
                receiptFooterDTO.getTotalQuantity(), receiptFooterDTO.getRawTotalPrice());
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


