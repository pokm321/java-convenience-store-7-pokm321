package store;

import store.controller.StoreController;
import store.util.MdReader;

public class Application {
    public static void main(String[] args) {

        StoreController controller = new StoreController();

        controller.run();
    }
}
