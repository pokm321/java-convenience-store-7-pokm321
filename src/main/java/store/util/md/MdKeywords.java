package store.util.md;

public enum MdKeywords {

    NULL("null"),
    NO_QUANTITY(0),
    PRODUCTS_PATH("src/main/resources/products.md"),
    PROMOTIONS_PATH("src/main/resources/promotions.md");

    private String text;
    private int number;

    MdKeywords(String text) {
        this.text = text;
    }

    MdKeywords(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public int getNumber() {
        return number;
    }
}
