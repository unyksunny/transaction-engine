package com.sunny.transaction_engine.interfaces.rest.advice;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(Exception ex, HttpServletRequest request) {
        log.info("404 Not Found: {}", request.getRequestURI());

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ErrorMessages.RESOURCE_NOT_FOUND,
                request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {
        log.info("405 Method Not Allowed: {} {}", request.getMethod(), request.getRequestURI());

        return buildErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                ErrorMessages.METHOD_NOT_ALLOWED,
                request);
    }

    // keep this at last
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error(ErrorMessages.INTERNAL_ERROR, ex);

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorMessages.INTERNAL_ERROR,
                request);
    }

    private ResponseEntity<ApiError> buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI());

        return new ResponseEntity<>(error, status);
    }
}
