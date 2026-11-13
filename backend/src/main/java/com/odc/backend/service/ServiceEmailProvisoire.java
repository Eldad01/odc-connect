package com.odc.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.odc.backend.entite.Offre;
import com.odc.backend.entite.Utilisateur;

/**
 * Version provisoire de ServiceEmail : elle n'envoie rien, elle écrit seulement dans les journaux.
 * Elle permet à chacun de travailler en attendant la version réelle.
 * À SUPPRIMER par Jean-Jacques quand il ajoute sa version réelle (sinon Spring trouvera deux ServiceEmail).
 */
@Service
public class ServiceEmailProvisoire implements ServiceEmail {

    private static final Logger journal = LoggerFactory.getLogger(ServiceEmailProvisoire.class);

    @Override
    public void envoyerInvitation(Utilisateur participant, String lienActivation) {
        journal.info("[E-MAIL PROVISOIRE] Invitation a {} : {}", participant.getEmail(), lienActivation);
    }

    @Override
    public void envoyerReinitialisationParAdministrateur(Utilisateur participant, String lienReinitialisation) {
        journal.info("[E-MAIL PROVISOIRE] Reinitialisation (administrateur) a {} : {}", participant.getEmail(), lienReinitialisation);
    }

    @Override
    public void envoyerMotDePasseOublie(Utilisateur utilisateur, String lienReinitialisation) {
        journal.info("[E-MAIL PROVISOIRE] Mot de passe oublie a {} : {}", utilisateur.getEmail(), lienReinitialisation);
    }

    @Override
    public void envoyerNouvelleOffre(Utilisateur participant, Offre offre) {
        journal.info("[E-MAIL PROVISOIRE] Nouvelle offre {} a {}", offre.getId(), participant.getEmail());
    }

    @Override
    public void envoyerRappel(Utilisateur participant, Offre offre) {
        journal.info("[E-MAIL PROVISOIRE] Rappel pour l'offre {} a {}", offre.getId(), participant.getEmail());
    }
}
