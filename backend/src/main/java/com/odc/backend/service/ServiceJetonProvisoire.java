package com.odc.backend.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.odc.backend.entite.TypeJeton;
import com.odc.backend.entite.Utilisateur;

/**
 * Version provisoire de ServiceJeton : elle renvoie un jeton factice et n'enregistre rien.
 * Elle permet à Jean-Jacques de construire ses liens d'invitation en attendant la version réelle.
 * À SUPPRIMER par Isabel quand elle ajoute sa version réelle (sinon Spring trouvera deux ServiceJeton).
 */
@Service
public class ServiceJetonProvisoire implements ServiceJeton {

    private static final Logger journal = LoggerFactory.getLogger(ServiceJetonProvisoire.class);

    @Override
    public String creerJeton(Utilisateur utilisateur, TypeJeton type) {
        String valeur = UUID.randomUUID().toString();
        journal.info("[JETON PROVISOIRE] {} pour {} : {}", type, utilisateur.getEmail(), valeur);
        return valeur;
    }

    @Override
    public Utilisateur consommerJeton(String valeur, TypeJeton type) {
        throw new UnsupportedOperationException("Verification des jetons : version reelle d'Isabel");
    }

    @Override
    public void invaliderJetons(Utilisateur utilisateur, TypeJeton type) {
        journal.info("[JETON PROVISOIRE] Invalidation des jetons {} de {}", type, utilisateur.getEmail());
    }
}
