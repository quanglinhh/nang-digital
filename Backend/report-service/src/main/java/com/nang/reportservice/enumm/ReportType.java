package com.nang.reportservice.enumm;

import java.util.Arrays;

public enum ReportType {
    MINUTE(1),
    HOUR(2),
    DAY(3);

    private final int code;

    ReportType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ReportType fromCode(int code) {
        return Arrays.stream(values())
                .filter(t -> t.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid report type: " + code));
    }
}
