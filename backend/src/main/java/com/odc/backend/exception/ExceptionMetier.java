package com.odc.backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe mère des erreurs métier. Chaque erreur porte son code HTTP.
 * Le gestionnaire d'erreurs la transforme en réponse JSON.
 */
public abstract class ExceptionMetier extends RuntimeException {

    private final HttpStatus statut;

    protected ExceptionMetier(HttpStatus statut, String message) {
        super(message);
        this.statut = statut;
    }

    public HttpStatus getStatut() {
        return statut;
    }
}
