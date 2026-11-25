package loja.taxas;

import loja.precos.Money;

/**
 * Logistics decides this. Today it looks exactly like ReturnFee, and that is fine:
 * the two rules change for different reasons and at different times.
 */
public final class ShippingFee {

    private static final Money FREE_ABOVE = Money.of("50.00");
    private static final Money FEE = Money.of("3.99");

    private ShippingFee() {
    }

    public static Money forOrder(Money subtotal) {
        return subtotal.compareTo(FREE_ABOVE) >= 0 ? Money.ZERO : FEE;
    }
}
