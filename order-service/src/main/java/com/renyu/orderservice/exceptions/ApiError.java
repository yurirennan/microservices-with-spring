package com.renyu.orderservice.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {
    private String resume;
    private HttpStatus status;
    private List<String> errors;

    public ApiError(final HttpStatus status, final String resume, final String error) {
        this.status = status;
        this.resume = resume;
        this.errors = Collections.singletonList(error);
    }

    public ApiError(final HttpStatus status, final String resume, final List<String> errors) {
        this.status = status;
        this.resume = resume;
        this.errors = errors;
    }

}
