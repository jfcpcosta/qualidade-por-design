package loja.checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** Owned by the payments team. Decides how much we charge the card. */
public class CheckoutService {

    public BigDecimal totalToCharge(Order order) {
        BigDecimal subtotal = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            BigDecimal lineTotal = line.unitPrice().multiply(BigDecimal.valueOf(line.quantity()));
            subtotal = subtotal.add(lineTotal);
        }

        BigDecimal discount = BigDecimal.ZERO;

        if ("BEMVINDO10".equals(order.couponCode())) {
            discount = new BigDecimal("0.10");
        }

        BigDecimal total = subtotal.multiply(BigDecimal.ONE.subtract(discount));

        return total.setScale(2, RoundingMode.HALF_EVEN);
    }
}
