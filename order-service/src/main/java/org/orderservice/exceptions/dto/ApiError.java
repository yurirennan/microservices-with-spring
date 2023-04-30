package org.orderservice.exceptions.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private final HttpStatus httpStatus;
    private final String message;
    private final List<String> errors;


    public ApiError(final HttpStatus httpStatus, final String message, final List<String> errors) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(final HttpStatus httpStatus, final String message, final String error) {
        super();
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}