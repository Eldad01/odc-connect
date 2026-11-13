package com.odc.backend.exception;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Format unique des erreurs (CDCT 4.4) :
 * date, code HTTP, message en français et, pour une erreur de validation,
 * la liste des champs en erreur.
 */
public record ReponseErreur(
        Instant date,
        int code,
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<ErreurChamp> champs) {

    public static ReponseErreur de(int code, String message) {
        return new ReponseErreur(Instant.now(), code, message, List.of());
    }

    public static ReponseErreur de(int code, String message, List<ErreurChamp> champs) {
        return new ReponseErreur(Instant.now(), code, message, champs);
    }
}
