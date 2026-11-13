package com.odc.backend.exception;

import org.springframework.http.HttpStatus;

/** 410 : lien d'activation ou de réinitialisation expiré ou déjà utilisé. */
public class LienExpireException extends ExceptionMetier {

    public LienExpireException(String message) {
        super(HttpStatus.GONE, message);
    }
}
