package com.example.gitbank.common.rest;

public class ResponseBuilder {

    public ResponseBuilder() {

    }

    public static <T> ApiResponse<T> build(T item) {
        return new ApiResponse<>(item);
    }

    public static ApiResponse<ErrorResponse> build(ErrorResponse errorResponse) {
        return new ApiResponse<>(errorResponse);
    }
}
