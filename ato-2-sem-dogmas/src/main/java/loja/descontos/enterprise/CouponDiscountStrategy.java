package loja.descontos.enterprise;

import java.math.BigDecimal;

public class CouponDiscountStrategy extends AbstractDiscountStrategy {

    @Override
    public DiscountType getType() {
        return DiscountType.COUPON;
    }

    @Override
    public boolean isApplicable(DiscountContext context) {
        return "BEMVINDO10".equals(context.getOrder().couponCode());
    }

    @Override
    protected BigDecimal doCalculateRate(DiscountContext context) {
        return new BigDecimal("0.10");
    }
}
