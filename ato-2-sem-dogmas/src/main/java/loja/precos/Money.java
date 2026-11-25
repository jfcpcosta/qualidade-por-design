package loja.precos;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Euros with cents. Rounding happens here and nowhere else. */
public record Money(BigDecimal amount) implements Comparable<Money> {

    public static final Money ZERO = Money.of("0.00");

    public Money {
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public Money plus(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money minus(Money other) {
        return new Money(amount.subtract(other.amount));
    }

    public Money times(int quantity) {
        return new Money(amount.multiply(BigDecimal.valueOf(quantity)));
    }

    public Money percentage(BigDecimal rate) {
        return new Money(amount.multiply(rate));
    }

    public long cents() {
        return amount.movePointRight(2).longValueExact();
    }

    @Override
    public int compareTo(Money other) {
        return amount.compareTo(other.amount);
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " €";
    }
}
