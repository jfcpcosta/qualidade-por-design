package loja.descontos.enterprise;

import java.math.BigDecimal;

public interface DiscountStrategy {

    DiscountType getType();

    boolean isApplicable(DiscountContext context);

    BigDecimal calculateRate(DiscountContext context);
}
