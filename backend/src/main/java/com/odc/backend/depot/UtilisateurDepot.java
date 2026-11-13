package com.odc.backend.depot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Utilisateur;

/** Accès à la table utilisateur. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface UtilisateurDepot extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}
