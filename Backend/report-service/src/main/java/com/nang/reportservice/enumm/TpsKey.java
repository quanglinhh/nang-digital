package com.nang.reportservice.enumm;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Getter
@AllArgsConstructor
public enum TpsKey {
    MINUTE("TPS:minutes", DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm"), ChronoUnit.MINUTES, true),
    HOUR("TPS:hours", DateTimeFormatter.ofPattern("yyyy-MM-dd_HH"), ChronoUnit.HOURS, true),
    DAY("TPS:days", DateTimeFormatter.ofPattern("yyyy-MM-dd"), ChronoUnit.DAYS, true),
    NEAREST_10_MINUTES("tps_data", null, null, false); // fixed key

    private final String redisKeyPrefix;
    private final DateTimeFormatter formatter;
    private final ChronoUnit unit;
    private final boolean appendTime;

    public String buildRedisKey(LocalDateTime time) {
        if (!appendTime) {
            return redisKeyPrefix;
        }
        return String.format("%s_%s", redisKeyPrefix, time.format(formatter));
    }
}
