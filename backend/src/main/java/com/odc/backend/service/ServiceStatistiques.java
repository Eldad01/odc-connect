package com.odc.backend.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odc.backend.depot.DepotStatistiques;
import com.odc.backend.dto.StatistiquesReponse;
import com.odc.backend.dto.StatistiquesReponse.Comptes;
import com.odc.backend.dto.StatistiquesReponse.ComptesFormation;
import com.odc.backend.dto.StatistiquesReponse.ConsultationOffre;
import com.odc.backend.dto.StatistiquesReponse.Offres;
import com.odc.backend.entite.StatutCompte;
import com.odc.backend.entite.StatutOffre;

/** Calcule les statistiques de la plateforme (US 09). */
@Service
public class ServiceStatistiques {

    private final DepotStatistiques depotStatistiques;

    public ServiceStatistiques(DepotStatistiques depotStatistiques) {
        this.depotStatistiques = depotStatistiques;
    }

    @Transactional(readOnly = true)
    public StatistiquesReponse calculer() {
        List<ComptesFormation> parFormation = comptesParFormation();
        return new StatistiquesReponse(
                Instant.now(),
                offres(),
                comptesGlobaux(parFormation),
                parFormation,
                consultations());
    }

    private Offres offres() {
        Map<StatutOffre, Long> nombres = new EnumMap<>(StatutOffre.class);
        for (Object[] ligne : depotStatistiques.compterOffresParStatut()) {
            nombres.put((StatutOffre) ligne[0], (Long) ligne[1]);
        }
        long actives = nombres.getOrDefault(StatutOffre.ACTIVE, 0L);
        long cloturees = nombres.getOrDefault(StatutOffre.CLOTUREE, 0L);
        long expirees = nombres.getOrDefault(StatutOffre.EXPIREE, 0L);
        return new Offres(actives, cloturees, expirees, actives + cloturees + expirees);
    }

    private List<ComptesFormation> comptesParFormation() {
        // formationId -> [intitule, nombres par statut], dans l'ordre alphabétique
        Map<Long, String> intitules = new LinkedHashMap<>();
        Map<Long, Map<StatutCompte, Long>> nombres = new LinkedHashMap<>();
        for (Object[] ligne : depotStatistiques.compterParticipantsParFormationEtStatut()) {
            Long formationId = (Long) ligne[0];
            intitules.put(formationId, (String) ligne[1]);
            Map<StatutCompte, Long> parStatut = nombres.computeIfAbsent(formationId, id -> new EnumMap<>(StatutCompte.class));
            if (ligne[2] != null) {
                parStatut.put((StatutCompte) ligne[2], (Long) ligne[3]);
            }
        }
        List<ComptesFormation> resultat = new ArrayList<>();
        for (Map.Entry<Long, String> formation : intitules.entrySet()) {
            Map<StatutCompte, Long> parStatut = nombres.get(formation.getKey());
            long invitation = parStatut.getOrDefault(StatutCompte.INVITATION_ENVOYEE, 0L);
            long actifs = parStatut.getOrDefault(StatutCompte.ACTIF, 0L);
            long suspendus = parStatut.getOrDefault(StatutCompte.SUSPENDU_INACTIF, 0L);
            long total = invitation + actifs + suspendus;
            resultat.add(new ComptesFormation(formation.getKey(), formation.getValue(),
                    invitation, actifs, suspendus, total, pourcentage(actifs, total)));
        }
        return resultat;
    }

    private Comptes comptesGlobaux(List<ComptesFormation> parFormation) {
        long invitation = 0;
        long actifs = 0;
        long suspendus = 0;
        for (ComptesFormation c : parFormation) {
            invitation += c.invitationEnvoyee();
            actifs += c.actifs();
            suspendus += c.suspendusInactifs();
        }
        long total = invitation + actifs + suspendus;
        return new Comptes(invitation, actifs, suspendus, total, pourcentage(actifs, total));
    }

    private List<ConsultationOffre> consultations() {
        return depotStatistiques.listerConsultations().stream()
                .map(l -> new ConsultationOffre((Long) l[0], (String) l[1], (StatutOffre) l[2], (Integer) l[3]))
                .toList();
    }

    /** Pourcentage arrondi à une décimale ; 0 s'il n'y a aucun compte. */
    private static double pourcentage(long partie, long total) {
        if (total == 0) {
            return 0;
        }
        return Math.round(partie * 1000.0 / total) / 10.0;
    }
}
