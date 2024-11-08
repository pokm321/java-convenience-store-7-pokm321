package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public String askFreeAvailable(String name, int freeAvailable) {
        System.out.printf(OutputMessages.ADD_PROMOTED_PRODUCT.getMessage(), name, freeAvailable);
        return Console.readLine();
    }
}
