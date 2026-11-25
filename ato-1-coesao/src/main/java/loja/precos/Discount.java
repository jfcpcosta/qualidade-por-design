package loja.precos;

import java.math.BigDecimal;

/** Every kind of discount the shop knows. The compiler knows the full list. */
public sealed interface Discount {

    BigDecimal rate();

    record Coupon(String code, BigDecimal rate) implements Discount {
    }

    record Campaign(String name, BigDecimal rate) implements Discount {
    }
}
