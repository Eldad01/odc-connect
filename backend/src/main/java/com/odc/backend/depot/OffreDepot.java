package com.odc.backend.depot;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Offre;

/** Accès à la table offre. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface OffreDepot extends JpaRepository<Offre, Long> {
}
