package com.example.gitbank.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CustomerRegistrationException extends RuntimeException {

    private final String customer;

    private final String message;

    public CustomerRegistrationException(String customer, String message) {
        super(String.format("Failed to register Customer[%s] : '%s'", customer, message));
        this.customer = customer;
        this.message = message;
    }

}
