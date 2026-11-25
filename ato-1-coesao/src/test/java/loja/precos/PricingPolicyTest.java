package loja.precos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

class PricingPolicyTest {

    private final PricingPolicy pricing = new PricingPolicy();

    private static final Discount WELCOME = new Discount.Coupon("BEMVINDO10", new BigDecimal("0.10"));
    private static final Discount BLACK_FRIDAY = new Discount.Campaign("Black Friday", new BigDecimal("0.20"));

    @Test
    void fullPriceWithoutDiscounts() {
        PriceBreakdown price = pricing.price(order(line("49.99", 2)), List.of());

        assertEquals(Money.of("99.98"), price.total());
    }

    @Test
    void couponAndCampaignAddUp() {
        PriceBreakdown price = pricing.price(order(line("100.00", 1)), List.of(WELCOME, BLACK_FRIDAY));

        assertEquals(Money.of("70.00"), price.total());
    }

    @Test
    void combinedDiscountIsCappedAtThirtyPercent() {
        Discount bigCampaign = new Discount.Campaign("Liquidação", new BigDecimal("0.25"));

        PriceBreakdown price = pricing.price(order(line("100.00", 1)), List.of(WELCOME, bigCampaign));

        assertEquals(new BigDecimal("0.30"), price.discountRate());
        assertEquals(Money.of("70.00"), price.total());
    }

    @Test
    void roundsOnceOnTheTotal() {
        PriceBreakdown price = pricing.price(order(line("7.95", 3), line("12.49", 1)), List.of(WELCOME));

        assertEquals(Money.of("36.34"), price.subtotal());
        assertEquals(Money.of("32.71"), price.total());
        assertEquals(3271, price.total().cents());
    }

    private static Order order(OrderLine... lines) {
        return new Order("T-1", "teste@exemplo.pt", List.of(lines), null);
    }

    private static OrderLine line(String price, int quantity) {
        return new OrderLine("SKU", new BigDecimal(price), quantity);
    }
}
