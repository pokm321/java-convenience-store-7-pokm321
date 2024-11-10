package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readItem() {
        System.out.printf(ViewMessages.ASK_ORDER.getMessage());
        return Console.readLine();
    }

    public boolean isAddingFree(String name, int freeCount) {
        System.out.printf(ViewMessages.ADD_PROMOTED_PRODUCT.getMessage(), name, freeCount);
        return convertYesOrNo(Console.readLine());
    }

    public boolean isGoingNoPromotionPrice(String name, int noPromotionCount) {
        System.out.printf(ViewMessages.NOT_ENOUGH_PROMOTED_QUANTITY.getMessage(), name, noPromotionCount);
        return convertYesOrNo(Console.readLine());
    }

    public boolean isMembership() {
        System.out.printf(ViewMessages.ASK_MEMBERSHIP.getMessage());
        return convertYesOrNo(Console.readLine());
    }

    public boolean isGoingAnotherShopping() {
        System.out.printf(ViewMessages.ASK_ANOTHER_ORDER.getMessage());
        return convertYesOrNo(Console.readLine());
    }

    private boolean convertYesOrNo(String input) {
        if (input.equals(ViewMessages.YES.getMessage())) {
            return true;
        }
        if (input.equals(ViewMessages.NO.getMessage())) {
            return false;
        }
        throw new IllegalArgumentException(ViewErrors.OTHERS.getMessage());
    }
}
