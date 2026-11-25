package loja.taxas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import loja.precos.Money;

class FeesTest {

    @Test
    void shippingIsFreeFromFiftyEuros() {
        assertEquals(Money.ZERO, ShippingFee.forOrder(Money.of("50.00")));
        assertEquals(Money.of("3.99"), ShippingFee.forOrder(Money.of("49.99")));
    }

    @Test
    void returnsFollowTheirOwnRule() {
        assertEquals(Money.ZERO, ReturnFee.forReturn(Money.of("60.00")));
        assertEquals(Money.of("3.99"), ReturnFee.forReturn(Money.of("20.00")));
    }

    @Test
    void theSharedCalculatorNeedsFiveArgumentsToSayTheSame() {
        assertEquals(Money.of("3.99"), FeeCalculator.calculate(Money.of("20.00"), true, false, false, false));
    }
}
