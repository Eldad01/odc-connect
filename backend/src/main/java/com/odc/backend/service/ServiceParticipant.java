
package com.odc.backend.service;

import com.odc.backend.depot.FormationDepot;
import com.odc.backend.depot.UtilisateurDepot;
import com.odc.backend.dto.ParticipantCreationRequete;
import com.odc.backend.entite.Formation;
import com.odc.backend.entite.Role;
import com.odc.backend.entite.StatutCompte;
import com.odc.backend.entite.TypeJeton;
import com.odc.backend.entite.Utilisateur;
import com.odc.backend.exception.ConflitException;
import com.odc.backend.exception.RessourceIntrouvableException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ServiceParticipant {

    private final UtilisateurDepot utilisateurDepot;
    private final FormationDepot formationDepot;
    private final ServiceJeton serviceJeton;
    private final ServiceEmail serviceEmail;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String urlFrontend;

    public ServiceParticipant(
            UtilisateurDepot utilisateurDepot,
            FormationDepot formationDepot,
            ServiceJeton serviceJeton,
            ServiceEmail serviceEmail) {
        this.utilisateurDepot = utilisateurDepot;
        this.formationDepot = formationDepot;
        this.serviceJeton = serviceJeton;
        this.serviceEmail = serviceEmail;
    }

    @Transactional
    public Long inscrire(ParticipantCreationRequete requete) {

        String email = requete.email().trim().toLowerCase();

        // Vérifier si l'adresse e-mail existe déjà
        if (utilisateurDepot.existsByEmailIgnoreCase(email)) {
            throw new ConflitException(
                    "Un utilisateur possède déjà cette adresse e-mail.");
        }

        // Vérifier que la formation existe
        Formation formation = formationDepot.findById(requete.formationId())
                .orElseThrow(() ->
                        new RessourceIntrouvableException("Formation introuvable."));

        // Créer le participant
        Utilisateur participant = new Utilisateur(
                requete.nom().trim(),
                requete.prenom().trim(),
                email,
                Role.PARTICIPANT,
                StatutCompte.INVITATION_ENVOYEE,
                formation
        );

        participant = utilisateurDepot.save(participant);

        // Générer le jeton d'activation
        String jeton = serviceJeton.creerJeton(
                participant,
                TypeJeton.ACTIVATION
        );

        // Construire le lien d'activation
        String lienActivation = UriComponentsBuilder
                .fromUriString(urlFrontend)
                .path("/activation")
                .queryParam("token", jeton)
                .build()
                .encode()
                .toUriString();

        // Envoyer l'invitation
        serviceEmail.envoyerInvitation(participant, lienActivation);

        return participant.getId();
    }
}
