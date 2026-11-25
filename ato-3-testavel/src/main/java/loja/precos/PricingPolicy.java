package loja.precos;

import java.math.BigDecimal;
import java.util.List;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** The single place that knows how an order is priced. */
public class PricingPolicy {

    static final BigDecimal MAX_DISCOUNT = new BigDecimal("0.30");

    public PriceBreakdown price(Order order, List<Discount> discounts) {
        Money subtotal = subtotal(order);
        BigDecimal rate = combinedRate(discounts);
        Money discount = subtotal.percentage(rate);

        return new PriceBreakdown(subtotal, rate, discount, subtotal.minus(discount));
    }

    private static Money subtotal(Order order) {
        Money subtotal = Money.ZERO;

        for (OrderLine line : order.lines()) {
            subtotal = subtotal.plus(new Money(line.unitPrice()).times(line.quantity()));
        }

        return subtotal;
    }

    private static BigDecimal combinedRate(List<Discount> discounts) {
        BigDecimal rate = discounts.stream()
                .map(Discount::rate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return rate.min(MAX_DISCOUNT);
    }
}
