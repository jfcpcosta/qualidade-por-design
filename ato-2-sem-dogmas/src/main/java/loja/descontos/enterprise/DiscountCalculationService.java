package loja.descontos.enterprise;

import java.math.BigDecimal;

import loja.encomendas.Order;

public class DiscountCalculationService {

    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("0.30");

    public BigDecimal totalRate(Order order, boolean campaignActive) {
        DiscountContext context = new DiscountContext(order, campaignActive);
        DiscountStrategyFactory factory = DiscountStrategyFactoryProvider.getFactory();
        BigDecimal rate = BigDecimal.ZERO;

        for (DiscountType type : DiscountType.values()) {
            rate = rate.add(factory.create(type).calculateRate(context));
        }

        return rate.min(MAX_DISCOUNT);
    }
}
