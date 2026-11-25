package loja.web;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import loja.campanhas.ActiveCampaigns;
import loja.campanhas.Campaign;
import loja.precos.PricingPolicy;

/** The only place that knows about Spring. The domain classes have no annotations. */
@Configuration
public class LojaConfiguration {

    private static final ZoneId MADRID = ZoneId.of("Europe/Madrid");

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    PricingPolicy pricingPolicy() {
        return new PricingPolicy();
    }

    @Bean
    ActiveCampaigns activeCampaigns(Clock clock) {
        Campaign blackFriday = new Campaign("Black Friday", new BigDecimal("0.20"),
                ZonedDateTime.of(2026, 11, 27, 0, 0, 0, 0, MADRID),
                ZonedDateTime.of(2026, 11, 28, 0, 0, 0, 0, MADRID));

        return new ActiveCampaigns(clock, List.of(blackFriday));
    }
}
