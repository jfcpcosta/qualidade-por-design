package loja.descontos.enterprise;

import java.math.BigDecimal;

/** Template method so that every strategy "follows the same lifecycle". */
public abstract class AbstractDiscountStrategy implements DiscountStrategy {

    @Override
    public final BigDecimal calculateRate(DiscountContext context) {
        if (!isApplicable(context)) {
            return BigDecimal.ZERO;
        }

        return doCalculateRate(context);
    }

    protected abstract BigDecimal doCalculateRate(DiscountContext context);
}
