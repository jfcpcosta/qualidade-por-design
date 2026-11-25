package loja.estados;

import loja.estados.OrderStatus.Paid;
import loja.estados.OrderStatus.PartiallyRefunded;
import loja.estados.OrderStatus.Pending;
import loja.estados.OrderStatus.Refunded;
import loja.estados.OrderStatus.Shipped;
import loja.precos.Money;

public final class RefundPolicy {

    private RefundPolicy() {
    }

    public static Money maxRefund(OrderStatus status, Money orderTotal) {
        return switch (status) {
            case Pending pending -> Money.ZERO;
            case Paid paid -> orderTotal;
            case Shipped shipped -> orderTotal;
            case PartiallyRefunded partial -> orderTotal.minus(partial.amount());
            case Refunded refunded -> Money.ZERO;
        };
    }
}
