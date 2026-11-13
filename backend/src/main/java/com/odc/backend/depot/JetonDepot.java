package com.odc.backend.depot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Jeton;

/** Accès à la table jeton. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface JetonDepot extends JpaRepository<Jeton, Long> {

    Optional<Jeton> findByValeurHachee(String valeurHachee);
}
