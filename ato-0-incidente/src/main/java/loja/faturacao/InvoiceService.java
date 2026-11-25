package loja.faturacao;

import java.math.BigDecimal;
import java.math.RoundingMode;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** Owned by the finance team. The invoice is a legal document. */
public class InvoiceService {

    public BigDecimal invoiceTotal(Order order) {
        BigDecimal discountRate = BigDecimal.ZERO;

        if (order.couponCode() != null && order.couponCode().equals("BEMVINDO10")) {
            discountRate = BigDecimal.valueOf(10);
        }

        BigDecimal total = BigDecimal.ZERO;

        // Each invoice line shows its own discounted value, rounded on the line.
        for (OrderLine line : order.lines()) {
            BigDecimal gross = line.unitPrice().multiply(BigDecimal.valueOf(line.quantity()));
            BigDecimal discount = gross.multiply(discountRate).divide(BigDecimal.valueOf(100));
            BigDecimal net = gross.subtract(discount).setScale(2, RoundingMode.HALF_UP);
            total = total.add(net);
        }

        return total;
    }
}
