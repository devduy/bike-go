package com.example.bikego.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum BikeFilter {
     RUNNING("ĐANG_CHẠY"),
     GIVING("ĐANG_GIAO"),
     NOTGIVE("CHƯA_GIAO");

    private final String status;

    BikeFilter(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
