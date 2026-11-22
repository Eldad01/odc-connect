package com.odc.backend.depot;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.odc.backend.entite.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Requêtes de comptage pour les statistiques (US 09).
 * Les calculs sont faits par la base (GROUP BY) pour rester sous 3 secondes.
 */
@Repository
public class DepotStatistiques {

    @PersistenceContext
    private EntityManager entityManager;

    /** Lignes [statut, nombre] des offres. */
    public List<Object[]> compterOffresParStatut() {
        return entityManager.createQuery(
                "select o.statut, count(o) from Offre o group by o.statut", Object[].class)
                .getResultList();
    }

    /**
     * Lignes [formationId, intitule, statut, nombre] des participants.
     * Une formation sans participant donne une ligne avec un statut vide et 0.
     */
    public List<Object[]> compterParticipantsParFormationEtStatut() {
        return entityManager.createQuery(
                "select f.id, f.intitule, u.statut, count(u) from Formation f "
                        + "left join Utilisateur u on u.formation = f and u.role = :role "
                        + "group by f.id, f.intitule, u.statut "
                        + "order by f.intitule", Object[].class)
                .setParameter("role", Role.PARTICIPANT)
                .getResultList();
    }

    /** Lignes [offreId, titre, statut, nombreConsultations], de la plus consultée à la moins consultée. */
    public List<Object[]> listerConsultations() {
        return entityManager.createQuery(
                "select o.id, o.titre, o.statut, o.nombreConsultations from Offre o "
                        + "order by o.nombreConsultations desc, o.datePublication desc", Object[].class)
                .getResultList();
    }
}
