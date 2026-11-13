package com.odc.backend.entite;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/** Clé primaire composée de la table favori : participant + offre. */
@Embeddable
public class FavoriId implements Serializable {

    @Column(name = "participant_id")
    private Long participantId;

    @Column(name = "offre_id")
    private Long offreId;

    protected FavoriId() {
        // constructeur exigé par JPA
    }

    public FavoriId(Long participantId, Long offreId) {
        this.participantId = participantId;
        this.offreId = offreId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Long getOffreId() {
        return offreId;
    }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (!(autre instanceof FavoriId that)) {
            return false;
        }
        return Objects.equals(participantId, that.participantId) && Objects.equals(offreId, that.offreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, offreId);
    }
}
