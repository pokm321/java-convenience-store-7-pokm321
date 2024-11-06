package store.domain.input;

public enum InputErrors {
    ERROR("[ERROR]"),
    SPACE(" "),
    ASK_AGAIN("다시 입력해 주세요."),
    INVALID_FORMAT(getErrorMessage("올바르지 않은 형식으로 입력했습니다.")),
    INVALID_NAME(getErrorMessage("존재하지 않는 상품입니다.")),
    INVALID_QUANTITY(getErrorMessage("재고 수량을 초과하여 구매할 수 없습니다.")),
    OTHERS(getErrorMessage("잘못된 입력입니다."));

    private final String message;

    InputErrors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private static String getErrorMessage(String message) {
        return String.join(SPACE.message, ERROR.message, message, ASK_AGAIN.getMessage());
    }
}
