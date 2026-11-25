package loja.estados;

import java.time.Instant;

import loja.precos.Money;

/** Each state carries only the data that exists in that state. */
public sealed interface OrderStatus {

    record Pending() implements OrderStatus {
    }

    record Paid(Instant paidAt) implements OrderStatus {
    }

    record Shipped(Instant paidAt, String trackingCode) implements OrderStatus {
    }

    record Refunded(Money amount) implements OrderStatus {
    }
}
