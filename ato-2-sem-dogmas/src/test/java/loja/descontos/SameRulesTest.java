package loja.descontos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import loja.descontos.enterprise.DiscountCalculationService;
import loja.descontos.simples.Discounts;
import loja.encomendas.Order;
import loja.encomendas.OrderLine;

/** Ten classes or two: the behaviour is exactly the same. */
class SameRulesTest {

    @ParameterizedTest
    @CsvSource(nullValues = "-", value = {
            "-,          false, 0",
            "BEMVINDO10, false, 0.10",
            "-,          true,  0.20",
            "BEMVINDO10, true,  0.30",
    })
    void bothVersionsAgree(String coupon, boolean campaignActive, BigDecimal expectedRate) {
        Order order = new Order("T-1", "teste@exemplo.pt",
                List.of(new OrderLine("SKU", new BigDecimal("10.00"), 1)), coupon);

        BigDecimal enterprise = new DiscountCalculationService().totalRate(order, campaignActive);
        BigDecimal simple = Discounts.totalRate(Discounts.applicableTo(order, campaignActive));

        assertEquals(0, expectedRate.compareTo(enterprise));
        assertEquals(0, expectedRate.compareTo(simple));
    }
}
