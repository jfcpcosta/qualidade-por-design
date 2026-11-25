package loja.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import loja.campanhas.ActiveCampaigns;
import loja.encomendas.Order;
import loja.encomendas.OrderLine;
import loja.precos.Money;
import loja.precos.PriceBreakdown;
import loja.precos.PricingPolicy;

/** One test for the shell, with plain fakes. The rules are tested in PricingPolicyTest. */
class PlaceOrderTest {

    private final List<Money> charges = new ArrayList<>();
    private final List<String> saved = new ArrayList<>();
    private final List<String> emails = new ArrayList<>();

    @Test
    void chargesSavesAndNotifiesWithTheSamePrice() {
        Clock clock = Clock.fixed(Instant.parse("2026-11-20T10:00:00Z"), ZoneOffset.UTC);

        PlaceOrder placeOrder = new PlaceOrder(
                new PricingPolicy(),
                new ActiveCampaigns(clock, List.of()),
                (orderId, amount) -> charges.add(amount),
                (order, price) -> saved.add(order.id()),
                (order, price) -> emails.add(order.customerEmail()));

        Order order = new Order("E-1", "ana@exemplo.pt",
                List.of(new OrderLine("SKU", new BigDecimal("50.00"), 2)), "BEMVINDO10");

        PriceBreakdown price = placeOrder.place(order);

        assertEquals(Money.of("90.00"), price.total());
        assertEquals(List.of(Money.of("90.00")), charges);
        assertEquals(List.of("E-1"), saved);
        assertEquals(List.of("ana@exemplo.pt"), emails);
    }
}
