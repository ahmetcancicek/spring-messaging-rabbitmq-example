package com.example.gitbank.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class AccountRegistrationException extends RuntimeException {

    private final String account;
    private final String message;

    public AccountRegistrationException(String account, String message) {
        super(String.format("Failed to register Account[%s] : '%s'", account, message));
        this.account = account;
        this.message = message;
    }
}
