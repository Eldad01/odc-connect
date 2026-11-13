package com.odc.backend.depot;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Favori;
import com.odc.backend.entite.FavoriId;

/** Accès à la table favori. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface FavoriDepot extends JpaRepository<Favori, FavoriId> {
}
