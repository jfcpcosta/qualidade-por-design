package loja.precos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loja.encomendas.Order;

/** Which discounts apply to an order. Kept apart from how they are combined. */
public class Discounts {

    private final boolean blackFridayActive;

    public Discounts(boolean blackFridayActive) {
        this.blackFridayActive = blackFridayActive;
    }

    public List<Discount> applicableTo(Order order) {
        List<Discount> discounts = new ArrayList<>();

        if ("BEMVINDO10".equals(order.couponCode())) {
            discounts.add(new Discount.Coupon("BEMVINDO10", new BigDecimal("0.10")));
        }

        if (blackFridayActive) {
            discounts.add(new Discount.Campaign("Black Friday", new BigDecimal("0.20")));
        }

        return discounts;
    }
}
