package loja.checkout;

import loja.precos.Money;
import loja.precos.PriceBreakdown;

/** Charges what the pricing policy decided. It no longer calculates anything. */
public class CheckoutService {

    public Money totalToCharge(PriceBreakdown price) {
        return price.total();
    }
}
