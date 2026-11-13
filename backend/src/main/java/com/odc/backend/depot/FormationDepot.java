package com.odc.backend.depot;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Formation;

/** Accès à la table formation. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface FormationDepot extends JpaRepository<Formation, Long> {

    boolean existsByIntituleIgnoreCase(String intitule);
}
