package loja.campanhas;

import java.time.Clock;
import java.util.List;

import loja.precos.Discount;

/** Time comes in through the Clock, so tests decide what "now" is. */
public class ActiveCampaigns {

    private final Clock clock;
    private final List<Campaign> campaigns;

    public ActiveCampaigns(Clock clock, List<Campaign> campaigns) {
        this.clock = clock;
        this.campaigns = List.copyOf(campaigns);
    }

    public List<Discount> now() {
        return campaigns.stream()
                .filter(campaign -> campaign.isActiveAt(clock.instant()))
                .<Discount>map(campaign -> new Discount.Campaign(campaign.name(), campaign.rate()))
                .toList();
    }
}
