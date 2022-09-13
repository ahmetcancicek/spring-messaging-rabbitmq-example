package com.example.gitbank.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InsufficientMoneyTransferErrorException extends RuntimeException {
    private final String fromAccount;
    private final String toAccount;

    private final String message;

    public InsufficientMoneyTransferErrorException(String fromAccount, String toAccount, String message) {
        super(String.format("Failed to transfer money from [%s] to [%s] : '%s'", fromAccount, toAccount, message));
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.message = message;
    }
}
