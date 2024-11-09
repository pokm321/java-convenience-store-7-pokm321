package store.util;

import java.util.function.BiPredicate;
import java.util.function.Supplier;
import store.view.OutputView;

public class Retrier {

    OutputView outputView = new OutputView();

    public void tryUntilSuccess(Runnable function) {
        while (true) {
            try {
                function.run();
                break;
            } catch (IllegalArgumentException error) {
                outputView.printError(error.getMessage());
            }
        }
    }

    public <T> T tryUntilSuccess(Supplier<T> function) {
        while (true) {
            try {
                return function.get();
            } catch (IllegalArgumentException error) {
                outputView.printError(error.getMessage());
            }
        }
    }

    public <T, U> boolean tryUntilSuccess(BiPredicate<T, U> function, T t, U u) {
        while (true) {
            try {
                return function.test(t, u);
            } catch (IllegalArgumentException error) {
                outputView.printError(error.getMessage());
            }
        }
    }
}
