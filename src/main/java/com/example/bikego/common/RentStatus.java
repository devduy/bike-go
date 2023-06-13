package com.example.bikego.common;

public enum RentStatus {
    COMPLETED("Đã hoàn thành"),
    IN_ORDER("Chờ xác nhận"),
    IN_PROGRESS("Đang thuê");
    private final String value;
    RentStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
