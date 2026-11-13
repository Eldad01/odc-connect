package com.odc.backend.exception;

import org.springframework.http.HttpStatus;

/** 400 : la demande ne respecte pas une règle métier. */
public class RequeteInvalideException extends ExceptionMetier {

    public RequeteInvalideException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
