package store.service.asker;

import store.util.Retrier;
import store.view.InputView;

public class MembershipAsker {

    private final InputView inputView;
    private final Retrier retrier;

    public MembershipAsker(InputView inputView, Retrier retrier) {
        this.inputView = inputView;
        this.retrier = retrier;
    }

    public boolean askMembership() {
        return retrier.tryUntilSuccess(inputView::isMembership);
    }
}
