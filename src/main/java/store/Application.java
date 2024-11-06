package store;

import store.controller.StoreController;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        StoreController controller = new StoreController(inputView, outputView);

        controller.run();
    }
}
