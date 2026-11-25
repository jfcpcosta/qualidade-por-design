package loja.campanhas;

import java.time.LocalDateTime;

/**
 * How the campaign check looked before: it asks the machine for the time.
 * The server runs in UTC, the campaign was defined in Madrid time,
 * and no test can say "pretend it is 23:30 on Friday".
 */
public class LegacyCampaignCheck {

    private static final LocalDateTime BLACK_FRIDAY_ENDS = LocalDateTime.of(2026, 11, 27, 23, 59, 59);

    public boolean blackFridayActive() {
        return LocalDateTime.now().isBefore(BLACK_FRIDAY_ENDS);
    }
}
