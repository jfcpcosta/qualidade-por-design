package loja.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import loja.encomendas.Order;
import loja.encomendas.OrderLine;

class CheckoutServiceTest {

    private final CheckoutService checkout = new CheckoutService();

    @Test
    void chargesFullPriceWithoutCoupon() {
        Order order = order(null, line("49.99", 2));

        assertEquals(new BigDecimal("99.98"), checkout.totalToCharge(order));
    }

    @Test
    void appliesWelcomeCoupon() {
        Order order = order("BEMVINDO10", line("50.00", 2));

        assertEquals(new BigDecimal("90.00"), checkout.totalToCharge(order));
    }

    private static Order order(String coupon, OrderLine... lines) {
        return new Order("T-1", "teste@exemplo.pt", List.of(lines), coupon);
    }

    private static OrderLine line(String price, int quantity) {
        return new OrderLine("SKU", new BigDecimal(price), quantity);
    }
}
