package com.example.gitbank.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ApiResponse {
    private final String data;
    private final Boolean success;
    private final String timestamp;

    public ApiResponse(Boolean success, String data) {
        this.timestamp = Instant.now().toString();
        this.data = data;
        this.success = success;
    }
}