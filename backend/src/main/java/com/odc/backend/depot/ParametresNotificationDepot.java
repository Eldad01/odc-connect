package com.odc.backend.depot;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.ParametresNotification;

/** Accès à la table parametres_notification. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface ParametresNotificationDepot extends JpaRepository<ParametresNotification, Long> {
}
