package loja.descontos.enterprise;

import loja.encomendas.Order;

/** Everything any strategy might one day need, just in case. */
public class DiscountContext {

    private final Order order;
    private final boolean campaignActive;

    public DiscountContext(Order order, boolean campaignActive) {
        this.order = order;
        this.campaignActive = campaignActive;
    }

    public Order getOrder() {
        return order;
    }

    public boolean isCampaignActive() {
        return campaignActive;
    }
}
