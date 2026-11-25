package loja.campanhas;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;

/** A campaign is a time window with an explicit time zone. No hidden "now". */
public record Campaign(String name, BigDecimal rate, ZonedDateTime start, ZonedDateTime end) {

    public boolean isActiveAt(Instant moment) {
        return !moment.isBefore(start.toInstant()) && moment.isBefore(end.toInstant());
    }
}
