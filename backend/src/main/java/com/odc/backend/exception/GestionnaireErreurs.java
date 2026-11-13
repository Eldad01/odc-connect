package com.odc.backend.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Gestionnaire d'erreurs centralisé : toutes les erreurs sortent au même format JSON.
 * Isabel y ajoutera le cas AccessDeniedException (403) avec la sécurité.
 */
@RestControllerAdvice
public class GestionnaireErreurs {

    private static final Logger journal = LoggerFactory.getLogger(GestionnaireErreurs.class);

    /** Erreurs métier : 400, 404, 409, 410. */
    @ExceptionHandler(ExceptionMetier.class)
    public ResponseEntity<ReponseErreur> erreurMetier(ExceptionMetier e) {
        return reponse(e.getStatut(), e.getMessage());
    }

    /** 400 : champs invalides (@Valid sur un DTO). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ReponseErreur> validation(MethodArgumentNotValidException e) {
        List<ErreurChamp> champs = e.getBindingResult().getFieldErrors().stream()
                .map(f -> new ErreurChamp(f.getField(), f.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest()
                .body(ReponseErreur.de(400, "Certains champs sont invalides.", champs));
    }

    /** 400 : corps JSON absent ou mal écrit. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ReponseErreur> corpsIllisible(HttpMessageNotReadableException e) {
        return reponse(HttpStatus.BAD_REQUEST, "Le corps de la requête est absent ou mal formé.");
    }

    /** 400 : paramètre de mauvais type, par exemple /offres/abc. */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ReponseErreur> mauvaisType(MethodArgumentTypeMismatchException e) {
        return reponse(HttpStatus.BAD_REQUEST, "Le paramètre « " + e.getName() + " » a une valeur invalide.");
    }

    /** 400 : paramètre obligatoire manquant. */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ReponseErreur> parametreManquant(MissingServletRequestParameterException e) {
        return reponse(HttpStatus.BAD_REQUEST, "Le paramètre « " + e.getParameterName() + " » est obligatoire.");
    }

    /** 404 : adresse inconnue. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ReponseErreur> adresseInconnue(NoResourceFoundException e) {
        return reponse(HttpStatus.NOT_FOUND, "Cette adresse n'existe pas.");
    }

    /** 405 : méthode HTTP non prévue sur cette adresse. */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ReponseErreur> methodeRefusee(HttpRequestMethodNotSupportedException e) {
        return reponse(HttpStatus.METHOD_NOT_ALLOWED, "Méthode " + e.getMethod() + " non autorisée sur cette adresse.");
    }

    /** 500 : erreur imprévue. Le détail va dans les journaux, jamais dans la réponse. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ReponseErreur> erreurImprevue(Exception e) {
        journal.error("Erreur imprévue", e);
        return reponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue. Réessayez plus tard.");
    }

    private ResponseEntity<ReponseErreur> reponse(HttpStatus statut, String message) {
        return ResponseEntity.status(statut).body(ReponseErreur.de(statut.value(), message));
    }
}
