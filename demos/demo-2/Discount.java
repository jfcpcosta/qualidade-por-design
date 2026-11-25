package loja.descontos.simples;

import java.math.BigDecimal;

public sealed interface Discount {

    BigDecimal rate();

    record Coupon(String code) implements Discount {

        public BigDecimal rate() {
            return new BigDecimal("0.10");
        }
    }

    record LoyalCustomer() implements Discount {

        public BigDecimal rate() {
            return new BigDecimal("0.05");
        }
    }

    record Campaign(String name) implements Discount {

        public BigDecimal rate() {
            return new BigDecimal("0.20");
        }
    }
}
