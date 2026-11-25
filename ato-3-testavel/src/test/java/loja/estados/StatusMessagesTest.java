package loja.estados;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import loja.precos.Money;

class StatusMessagesTest {

    @Test
    void describesEveryState() {
        assertEquals("A aguardar pagamento", StatusMessages.forCustomer(new OrderStatus.Pending()));
        assertEquals("Reembolsada · 12.00 €",
                StatusMessages.forCustomer(new OrderStatus.Refunded(Money.of("12.00"))));
    }

    @Test
    void nothingToRefundBeforePayment() {
        assertEquals(Money.ZERO, RefundPolicy.maxRefund(new OrderStatus.Pending(), Money.of("80.00")));
    }
}
