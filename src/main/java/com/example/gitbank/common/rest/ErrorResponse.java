package com.example.gitbank.common.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    public String code;

    private String message;

    public ErrorResponse() {
        this.timestamp = new Date();
    }

    public ErrorResponse(String code,
                         String message) {
        this();
        this.code = code;
        this.message = message;
    }
}
