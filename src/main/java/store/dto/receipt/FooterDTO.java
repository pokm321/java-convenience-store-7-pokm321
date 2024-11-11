package store.dto.receipt;

public class FooterDTO {

    private final int totalQuantity;
    private final long rawTotalPrice;
    private final long promotionDiscount;
    private final long membershipDiscount;
    private final long payment;

    public FooterDTO(int totalQuantity, long rawTotalPrice, long promotionDiscount, long membershipDiscount,
                     long payment) {
        this.totalQuantity = totalQuantity;
        this.rawTotalPrice = rawTotalPrice;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.payment = payment;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public long getRawTotalPrice() {
        return rawTotalPrice;
    }

    public long getPromotionDiscount() {
        return promotionDiscount;
    }

    public long getMembershipDiscount() {
        return membershipDiscount;
    }

    public long getPayment() {
        return payment;
    }
}
