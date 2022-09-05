package com.example.gitbank.common.rest;

public class BaseController {

    protected <T> ApiResponse<T> respond(T item) {
        return ResponseBuilder.build(item);
    }

    protected ApiResponse<ErrorResponse> respond(ErrorResponse errorResponse) {
        return ResponseBuilder.build(errorResponse);
    }
}
