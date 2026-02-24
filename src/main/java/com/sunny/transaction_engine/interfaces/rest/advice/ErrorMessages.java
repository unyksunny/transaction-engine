package com.sunny.transaction_engine.interfaces.rest.advice;

public final class ErrorMessages {
    private ErrorMessages() {} // prevent instantiation

    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String METHOD_NOT_ALLOWED = "Method not allowed";
    public static final String INTERNAL_ERROR = "Unexpected internal error";
}
