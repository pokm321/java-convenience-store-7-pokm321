package store.util.md;

public enum MdKeywords {

    NULL("null");

    private final String value;

    MdKeywords(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
