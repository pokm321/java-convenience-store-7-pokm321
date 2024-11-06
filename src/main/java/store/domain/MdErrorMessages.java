package store.domain;

public enum MdErrorMessages {

    MD_READ_FAIL("파일 읽기에 실패했습니다."),
    MD_EMPTY_ERROR("값이 비어있습니다."),
    MD_COUNT_ERROR("필드 개수가 맞지 않습니다."),
    MD_INTEGER_ERROR("int 변환에 실패했습니다.");

    private final String message;

    MdErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
