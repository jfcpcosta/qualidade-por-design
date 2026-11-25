package loja.descontos.simples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loja.encomendas.Order;

public final class Discounts {

    private static final BigDecimal MAX_DISCOUNT = new BigDecimal("0.30");

    private Discounts() {
    }

    public static List<Discount> applicableTo(Order order, boolean campaignActive) {
        List<Discount> discounts = new ArrayList<>();

        if ("BEMVINDO10".equals(order.couponCode())) {
            discounts.add(new Discount.Coupon("BEMVINDO10"));
        }

        if (campaignActive) {
            discounts.add(new Discount.Campaign("Black Friday"));
        }

        return discounts;
    }

    public static BigDecimal totalRate(List<Discount> discounts) {
        BigDecimal rate = discounts.stream()
                .map(Discount::rate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return rate.min(MAX_DISCOUNT);
    }

    /** Shown on the invoice. No default branch: a new discount will not compile until it has a label. */
    public static String label(Discount discount) {
        return switch (discount) {
            case Discount.Coupon coupon -> "Cupão " + coupon.code();
            case Discount.Campaign campaign -> "Campanha " + campaign.name();
            case Discount.LoyalCustomer loyal -> "Cliente frequente";
        };
    }
}
