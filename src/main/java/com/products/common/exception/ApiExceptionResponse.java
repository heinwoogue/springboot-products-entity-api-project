package com.products.common.exception;

public class ApiExceptionResponse {
    private final String message;

    public ApiExceptionResponse(Exception e){
        message = e.getMessage();
    }

    public String getMessage() {
        return message;
    }
}
