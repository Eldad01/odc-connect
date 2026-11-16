package com.odc.backend.depot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odc.backend.entite.Formation;

/** Accès à la table formation. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface FormationDepot extends JpaRepository<Formation, Long> {

    boolean existsByIntituleIgnoreCase(String intitule);

    /** Même intitulé déjà pris par une autre formation (modification). */
    boolean existsByIntituleIgnoreCaseAndIdNot(String intitule, Long id);

    /** Toutes les formations, par ordre alphabétique. */
    List<Formation> findAllByOrderByIntituleAsc();

    /** Vrai si au moins un utilisateur est rattaché à cette formation. */
    @Query("select case when count(u) > 0 then true else false end from Utilisateur u where u.formation.id = :id")
    boolean aDesParticipants(@Param("id") Long id);

    /** Vrai si au moins une offre cible cette formation. */
    @Query("select case when count(o) > 0 then true else false end from Offre o join o.formations f where f.id = :id")
    boolean estCibleeParUneOffre(@Param("id") Long id);
}
