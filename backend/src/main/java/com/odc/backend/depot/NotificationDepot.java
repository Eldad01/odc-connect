package com.odc.backend.depot;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odc.backend.entite.Notification;

/** Accès à la table notification. Chacun y ajoute les requêtes dont il a besoin pour sa partie. */
public interface NotificationDepot extends JpaRepository<Notification, Long> {
}
