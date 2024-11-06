package store.view;

public enum OutputMessages {

    WELCOME("안녕하세요. W편의점입니다.%n현재 보유하고 있는 상품입니다.%n%n"),
    ITEM_INFO("- %s %,d원 "),
    QUANTITY("%,d개"),
    SPACE(" "),
    NO_QUANTITY_OUTPUT("재고 없음");

    private final String message;

    OutputMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
