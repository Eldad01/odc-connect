package com.odc.backend.convertisseur;

import com.odc.backend.dto.FormationReponse;
import com.odc.backend.entite.Formation;

/** Passe de l'entité Formation au DTO renvoyé par l'API. */
public final class FormationConvertisseur {

    private FormationConvertisseur() {
    }

    public static FormationReponse versReponse(Formation formation) {
        return new FormationReponse(formation.getId(), formation.getIntitule());
    }
}
