package com.odc.backend.exception;

/** Un champ en erreur lors d'une validation. */
public record ErreurChamp(String champ, String message) {
}
