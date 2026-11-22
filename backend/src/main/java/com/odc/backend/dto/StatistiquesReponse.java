package com.odc.backend.dto;

import java.time.Instant;
import java.util.List;

import com.odc.backend.entite.StatutOffre;

/**
 * Statistiques de la plateforme (US 09).
 *
 * @param genereLe            date de calcul
 * @param offres              nombre d'offres par statut (critère 1)
 * @param comptes             comptes des participants, toutes formations (objectif OQ 2)
 * @param comptesParFormation comptes par formation et par statut, avec le pourcentage d'actifs (critère 2)
 * @param consultations       consultations de chaque offre, de la plus vue à la moins vue (critère 3)
 */
public record StatistiquesReponse(
        Instant genereLe,
        Offres offres,
        Comptes comptes,
        List<ComptesFormation> comptesParFormation,
        List<ConsultationOffre> consultations) {

    public record Offres(long actives, long cloturees, long expirees, long total) {
    }

    public record Comptes(long invitationEnvoyee, long actifs, long suspendusInactifs, long total,
            double pourcentageActifs) {
    }

    public record ComptesFormation(Long formationId, String intitule, long invitationEnvoyee, long actifs,
            long suspendusInactifs, long total, double pourcentageActifs) {
    }

    public record ConsultationOffre(Long offreId, String titre, StatutOffre statut, int nombreConsultations) {
    }
}
