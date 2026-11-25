package loja.taxas;

import loja.precos.Money;

/** Customer service decides this. Same numbers as ShippingFee, different owner. */
public final class ReturnFee {

    private static final Money FREE_ABOVE = Money.of("50.00");
    private static final Money FEE = Money.of("3.99");

    private ReturnFee() {
    }

    public static Money forReturn(Money refunded) {
        return refunded.compareTo(FREE_ABOVE) >= 0 ? Money.ZERO : FEE;
    }
}
