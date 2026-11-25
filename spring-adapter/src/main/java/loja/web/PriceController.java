package loja.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import loja.campanhas.ActiveCampaigns;
import loja.encomendas.Order;
import loja.precos.Discount;
import loja.precos.PriceBreakdown;
import loja.precos.PricingPolicy;

/** HTTP in, domain decision, JSON out. No rule is written here. */
@RestController
public class PriceController {

    private final PricingPolicy pricing;
    private final ActiveCampaigns campaigns;

    public PriceController(PricingPolicy pricing, ActiveCampaigns campaigns) {
        this.pricing = pricing;
        this.campaigns = campaigns;
    }

    @PostMapping("/encomendas/preco")
    public PriceBreakdown price(@RequestBody Order order) {
        List<Discount> discounts = new ArrayList<>(campaigns.now());

        if ("BEMVINDO10".equals(order.couponCode())) {
            discounts.add(new Discount.Coupon("BEMVINDO10", new BigDecimal("0.10")));
        }

        return pricing.price(order, discounts);
    }
}
