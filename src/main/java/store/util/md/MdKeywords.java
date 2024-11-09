package store.util.md;

public enum MdKeywords {

    NULL("null"),
    PRODUCTS_PATH("src/main/resources/products.md"),
    PROMOTIONS_PATH("src/main/resources/promotions.md");

    private final String value;

    MdKeywords(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
