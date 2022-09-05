package com.example.gitbank.common.rest;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {

    private T data;

    private ErrorResponse errors;

    public ApiResponse() {

    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ErrorResponse errors) {
        this.errors = errors;
    }
}