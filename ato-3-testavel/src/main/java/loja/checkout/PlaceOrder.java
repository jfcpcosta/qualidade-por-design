package loja.checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loja.campanhas.ActiveCampaigns;
import loja.encomendas.Order;
import loja.precos.Discount;
import loja.precos.PriceBreakdown;
import loja.precos.PricingPolicy;

/**
 * The imperative shell: gathers inputs, asks the pure core for a decision,
 * then talks to the outside world. No business rule lives here.
 */
public class PlaceOrder {

    private final PricingPolicy pricing;
    private final ActiveCampaigns campaigns;
    private final PaymentGateway payments;
    private final OrderRepository orders;
    private final Notifier notifier;

    public PlaceOrder(PricingPolicy pricing, ActiveCampaigns campaigns, PaymentGateway payments,
                      OrderRepository orders, Notifier notifier) {
        this.pricing = pricing;
        this.campaigns = campaigns;
        this.payments = payments;
        this.orders = orders;
        this.notifier = notifier;
    }

    public PriceBreakdown place(Order order) {
        List<Discount> discounts = new ArrayList<>(campaigns.now());

        if ("BEMVINDO10".equals(order.couponCode())) {
            discounts.add(new Discount.Coupon("BEMVINDO10", new BigDecimal("0.10")));
        }

        PriceBreakdown price = pricing.price(order, discounts);

        payments.charge(order.id(), price.total());
        orders.save(order, price);
        notifier.orderConfirmed(order, price);

        return price;
    }
}
