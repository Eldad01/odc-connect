package com.odc.backend.service;

/**
 * Contrat partagé : événement émis quand une offre est publiée (CDCT, section 6.3).
 * Émis par : Barry, à la publication d'une offre, avec ApplicationEventPublisher.publishEvent(...).
 * Écouté par : Paré, pour notifier les participants des formations ciblées.
 * Il ne transporte que l'identifiant de l'offre : celui qui l'écoute relit l'offre en base.
 */
public record OffrePublieeEvenement(Long offreId) {
}
