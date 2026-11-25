package loja.taxas;

import loja.precos.Money;

/**
 * The "DRY" version, a year later. It started as one shared method and gained
 * a flag every time one of its callers needed something different.
 */
public final class FeeCalculator {

    private FeeCalculator() {
    }

    public static Money calculate(Money amount, boolean isReturn, boolean isExpress,
                                  boolean isIsland, boolean isPremiumCustomer) {
        Money freeAbove = isIsland ? Money.of("80.00") : Money.of("50.00");

        if (isReturn && isPremiumCustomer) {
            return Money.ZERO;
        }

        if (amount.compareTo(freeAbove) >= 0 && !isExpress) {
            return Money.ZERO;
        }

        if (isExpress) {
            return isReturn ? Money.of("3.99") : Money.of("7.99");
        }

        return isIsland && !isReturn ? Money.of("6.49") : Money.of("3.99");
    }
}
