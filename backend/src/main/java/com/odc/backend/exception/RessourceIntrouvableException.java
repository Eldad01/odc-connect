package com.odc.backend.exception;

import org.springframework.http.HttpStatus;

/** 404 : l'élément demandé n'existe pas. */
public class RessourceIntrouvableException extends ExceptionMetier {

    public RessourceIntrouvableException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
