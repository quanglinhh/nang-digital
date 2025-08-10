package com.nang.reportservice.enumm;

public enum TpsPeriod {
    NEAREST_10_MINUTES("Nearest 10 minutes"),
    FROM_START_OF_DAY("From start of day to now"),
    LAST_5_DAYS("Last 5 days");

    private final String description;

    TpsPeriod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}