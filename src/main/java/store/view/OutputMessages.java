package store.view;

public enum Outputs {

    WELCOME("안녕하세요. W편의점입니다.%n현재 보유하고 있는 상품입니다.%n%n"),
    ITEM_INFO("- %s %,d원 "),
    QUANTITY("%,d개"),
    SPACE(" "),
    NO_QUANTITY_OUTPUT("재고 없음"),
    ADD_PROMOTED_PRODUCT("현재 %s은(는) %,d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)");

    private final String message;

    Outputs(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
