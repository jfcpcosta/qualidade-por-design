package loja.estados;

import loja.estados.OrderStatus.Paid;
import loja.estados.OrderStatus.PartiallyRefunded;
import loja.estados.OrderStatus.Pending;
import loja.estados.OrderStatus.Refunded;
import loja.estados.OrderStatus.Shipped;

public final class StatusMessages {

    private StatusMessages() {
    }

    // No default branch on purpose: a new state must not compile until it has a message.
    public static String forCustomer(OrderStatus status) {
        return switch (status) {
            case Pending pending -> "A aguardar pagamento";
            case Paid paid -> "Pagamento recebido";
            case Shipped shipped -> "Enviada · " + shipped.trackingCode();
            case PartiallyRefunded partial -> "Reembolso parcial · " + partial.amount();
            case Refunded refunded -> "Reembolsada · " + refunded.amount();
        };
    }
}
