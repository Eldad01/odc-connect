package com.odc.backend.exception;

import org.springframework.http.HttpStatus;

/** 409 : conflit, par exemple adresse e-mail déjà enregistrée. */
public class ConflitException extends ExceptionMetier {

    public ConflitException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
