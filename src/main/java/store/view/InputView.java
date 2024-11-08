package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public boolean isAddingFree(String name, int freeCount) throws IllegalArgumentException {
        System.out.printf(OutputMessages.ADD_PROMOTED_PRODUCT.getMessage(), name, freeCount);
        return convertYesOrNo(Console.readLine());
    }

    public boolean isGoingNoPromotionPrice(String name, int noPromotionCount) throws IllegalArgumentException {
        System.out.printf(OutputMessages.NOT_ENOUGH_PROMOTED_QUANTITY.getMessage(), name, noPromotionCount);
        return convertYesOrNo(Console.readLine());
    }

    private boolean convertYesOrNo(String input) throws IllegalArgumentException {
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new IllegalArgumentException(InputErrors.OTHERS.getMessage());
    }
}
