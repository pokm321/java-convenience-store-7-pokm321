package store.util.md;

public enum MdPaths {

    PRODUCTS("src/main/resources/products.md"),
    PROMOTIONS("src/main/resources/promotions.md");

    private final String path;

    MdPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
