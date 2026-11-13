package com.odc.backend.service;

import com.odc.backend.entite.Offre;
import com.odc.backend.entite.Utilisateur;

/**
 * Contrat partagé : envoi des e-mails de l'application (CDCT, section 6.1).
 * Version réelle : Jean-Jacques (Spring Mail, Thymeleaf, envoi asynchrone, 3 essais).
 * Utilisé par : Isabel (activation, mot de passe oublié), Jean-Jacques (invitations), Paré (notifications, rappels).
 */
public interface ServiceEmail {

    /** Invitation d'un nouveau participant, avec le lien d'activation de son compte (US 01). */
    void envoyerInvitation(Utilisateur participant, String lienActivation);

    /** Lien de réinitialisation envoyé à la demande de l'administrateur (US 01). */
    void envoyerReinitialisationParAdministrateur(Utilisateur participant, String lienReinitialisation);

    /** Lien « Mot de passe oublié » demandé par le participant lui-même (US 07). */
    void envoyerMotDePasseOublie(Utilisateur utilisateur, String lienReinitialisation);

    /** Notification d'une nouvelle offre qui concerne la formation du participant (US 05). */
    void envoyerNouvelleOffre(Utilisateur participant, Offre offre);

    /** Rappel 24 heures avant la date limite d'une offre (US 05, US 08). */
    void envoyerRappel(Utilisateur participant, Offre offre);
}
