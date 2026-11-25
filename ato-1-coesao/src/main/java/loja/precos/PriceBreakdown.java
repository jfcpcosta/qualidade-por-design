package loja.precos;

import java.math.BigDecimal;

/** The price of an order, calculated once and shared by everyone who shows it. */
public record PriceBreakdown(Money subtotal, BigDecimal discountRate, Money discount, Money total) {
}
