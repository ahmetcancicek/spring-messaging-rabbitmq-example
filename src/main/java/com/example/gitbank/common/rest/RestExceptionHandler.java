package com.example.gitbank.common.rest;

import com.example.gitbank.common.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponse> handleExceptions(Exception exception) {
        log.error("An error occurred!: ", exception);
        return respond(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "System error occurred."));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ApiResponse<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return respond(new ErrorResponse(HttpStatus.NOT_FOUND.toString(), ""));
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(InsufficientErrorException.class)
    public ApiResponse<ErrorResponse> handleInsufficientErrorException(InsufficientErrorException insufficientErrorException) {
        return respond(new ErrorResponse(HttpStatus.EXPECTATION_FAILED.toString(), ""));
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(InsufficientMoneyTransferErrorException.class)
    public ApiResponse<ErrorResponse> handleInsufficientMoneyTransferErrorException(InsufficientMoneyTransferErrorException insufficientMoneyTransferErrorException) {
        return respond(new ErrorResponse(HttpStatus.EXPECTATION_FAILED.toString(), ""));
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(NotEqualCurrentException.class)
    public ApiResponse<ErrorResponse> handleNotEqualCurrentException(NotEqualCurrentException notEqualCurrentException) {
        return respond(new ErrorResponse(HttpStatus.EXPECTATION_FAILED.toString(), ""));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsElementException.class)
    public ApiResponse<ErrorResponse> handleAlreadyExistsElementException(AlreadyExistsElementException exception) {
        return respond(new ErrorResponse(HttpStatus.CONFLICT.toString(), ""));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomerRegistrationException.class)
    public ApiResponse<ErrorResponse> handleCustomerRegistrationException(CustomerRegistrationException exception) {
        return respond(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ""));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(AccountRegistrationException.class)
    public ApiResponse<ErrorResponse> accountRegistrationException(AccountRegistrationException accountRegistrationException) {
        return respond(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ""));
    }
}
