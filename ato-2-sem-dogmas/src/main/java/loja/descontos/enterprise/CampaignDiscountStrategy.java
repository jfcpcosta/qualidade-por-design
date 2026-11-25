package loja.descontos.enterprise;

import java.math.BigDecimal;

public class CampaignDiscountStrategy extends AbstractDiscountStrategy {

    @Override
    public DiscountType getType() {
        return DiscountType.CAMPAIGN;
    }

    @Override
    public boolean isApplicable(DiscountContext context) {
        return context.isCampaignActive();
    }

    @Override
    protected BigDecimal doCalculateRate(DiscountContext context) {
        return new BigDecimal("0.20");
    }
}
