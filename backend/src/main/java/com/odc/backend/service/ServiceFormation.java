package com.odc.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.odc.backend.convertisseur.FormationConvertisseur;
import com.odc.backend.depot.FormationDepot;
import com.odc.backend.dto.FormationReponse;
import com.odc.backend.dto.FormationRequete;
import com.odc.backend.entite.Formation;
import com.odc.backend.exception.ConflitException;
import com.odc.backend.exception.RessourceIntrouvableException;

/** Règles métier des formations (US 03, US 04). */
@Service
public class ServiceFormation {

    private static final Logger journal = LoggerFactory.getLogger(ServiceFormation.class);

    private final FormationDepot formationDepot;

    public ServiceFormation(FormationDepot formationDepot) {
        this.formationDepot = formationDepot;
    }

    @Transactional(readOnly = true)
    public List<FormationReponse> lister() {
        return formationDepot.findAllByOrderByIntituleAsc().stream()
                .map(FormationConvertisseur::versReponse)
                .toList();
    }

    @Transactional
    public FormationReponse creer(FormationRequete requete) {
        String intitule = requete.intitule().trim();
        if (formationDepot.existsByIntituleIgnoreCase(intitule)) {
            throw new ConflitException("La formation « " + intitule + " » existe déjà.");
        }
        Formation formation = formationDepot.save(new Formation(intitule));
        journal.info("Formation créée : {} (id {})", intitule, formation.getId());
        return FormationConvertisseur.versReponse(formation);
    }

    @Transactional
    public FormationReponse modifier(Long id, FormationRequete requete) {
        Formation formation = trouver(id);
        String intitule = requete.intitule().trim();
        if (formationDepot.existsByIntituleIgnoreCaseAndIdNot(intitule, id)) {
            throw new ConflitException("La formation « " + intitule + " » existe déjà.");
        }
        formation.setIntitule(intitule);
        journal.info("Formation modifiée : id {} -> {}", id, intitule);
        return FormationConvertisseur.versReponse(formation);
    }

    @Transactional
    public void supprimer(Long id) {
        Formation formation = trouver(id);
        if (formationDepot.aDesParticipants(id)) {
            throw new ConflitException("Impossible de supprimer cette formation : des participants y sont inscrits.");
        }
        if (formationDepot.estCibleeParUneOffre(id)) {
            throw new ConflitException("Impossible de supprimer cette formation : des offres la ciblent.");
        }
        formationDepot.delete(formation);
        journal.info("Formation supprimée : id {}", id);
    }

    private Formation trouver(Long id) {
        return formationDepot.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Formation introuvable."));
    }
}
