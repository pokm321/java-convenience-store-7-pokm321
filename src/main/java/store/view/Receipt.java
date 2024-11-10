package store.view;

public enum Receipt {

    HEADER_RECEIPT(System.lineSeparator() + "=============W 편의점==============="),
    HEADER_PRODUCTS("상품명              수량        금액"),

    HEADER_FREE_PRODUCTS("=============증    정==============="),

    FOOTER_RECEIPT("===================================="),
    ROW_TOTAL_PRICE("총구매액"),
    ROW_PROMOTION_DISCOUNT("행사할인"),
    ROW_MEMBERSHIP_DISCOUNT("멤버십할인"),
    ROW_PAYMENT("내실돈"),

    COLUMN_FORMAT_ALL_THREE("%s%,-5d%,11d\n"),
    COLUMN_FORMAT_FIRST_THIRD("%s%,16d\n"),
    COLUMN_FORMAT_FIRST_SECOND("%s%,d\n"),
    FIRST_COLUMN_LENGTH(20);

    private String text;
    private int number;

    Receipt(String text) {
        this.text = text;
    }

    Receipt(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }
}
