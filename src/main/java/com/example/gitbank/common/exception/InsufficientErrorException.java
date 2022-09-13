package com.example.gitbank.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class InsufficientErrorException extends RuntimeException {
    private final String fromAccount;

    private final String message;

    public InsufficientErrorException(String fromAccount, String message) {
        super(String.format("Failed to transfer money from [%s] : '%s'", fromAccount, message));
        this.fromAccount = fromAccount;
        this.message = message;
    }
}
