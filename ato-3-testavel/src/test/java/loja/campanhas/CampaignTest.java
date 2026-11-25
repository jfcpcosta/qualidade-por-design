package loja.campanhas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class CampaignTest {

    private static final ZoneId MADRID = ZoneId.of("Europe/Madrid");

    private static final Campaign BLACK_FRIDAY = new Campaign("Black Friday", new BigDecimal("0.20"),
            ZonedDateTime.of(2026, 11, 27, 0, 0, 0, 0, MADRID),
            ZonedDateTime.of(2026, 11, 28, 0, 0, 0, 0, MADRID));

    @Test
    void activeOnFridayAfternoon() {
        ActiveCampaigns campaigns = at("2026-11-27T16:00:00Z");

        assertEquals(1, campaigns.now().size());
    }

    @Test
    void overAtMidnightInMadridEvenIfItIsStillFridayInUtc() {
        // 23:30 UTC on Friday is already 00:30 on Saturday in Madrid.
        ActiveCampaigns campaigns = at("2026-11-27T23:30:00Z");

        assertTrue(campaigns.now().isEmpty());
    }

    private static ActiveCampaigns at(String instant) {
        Clock clock = Clock.fixed(Instant.parse(instant), ZoneOffset.UTC);

        return new ActiveCampaigns(clock, List.of(BLACK_FRIDAY));
    }
}
