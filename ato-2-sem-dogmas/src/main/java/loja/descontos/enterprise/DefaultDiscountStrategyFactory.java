package loja.descontos.enterprise;

import java.util.EnumMap;
import java.util.Map;

public class DefaultDiscountStrategyFactory implements DiscountStrategyFactory {

    private final Map<DiscountType, DiscountStrategy> registry = new EnumMap<>(DiscountType.class);

    public DefaultDiscountStrategyFactory() {
        registry.put(DiscountType.COUPON, new CouponDiscountStrategy());
        registry.put(DiscountType.CAMPAIGN, new CampaignDiscountStrategy());
    }

    @Override
    public DiscountStrategy create(DiscountType type) {
        DiscountStrategy strategy = registry.get(type);

        if (strategy == null) {
            throw new IllegalStateException("No strategy registered for " + type);
        }

        return strategy;
    }
}
