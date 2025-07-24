package com.nang.orderservice.enumm;

public enum OrderStatus {
    DEPOSITED(0), // Đã đặt cọc
    RENTING(1), // Đang thuê
    COMPLETED(2), // Đã trả máy
    EXPENSE(3); // Khoản chi (sửa máy, xăng, v.v.)

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus fromValue(int value) {
        for (OrderStatus status : values()) {
            if (status.value == value) return status;
        }
        throw new IllegalArgumentException("Unknown OrderStatus value: " + value);
    }
}
