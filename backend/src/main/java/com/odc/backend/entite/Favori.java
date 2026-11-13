package com.odc.backend.entite;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/** Offre enregistrée en favori par un participant (US 08). */
@Entity
@Table(name = "favori")
public class Favori {

    @EmbeddedId
    private FavoriId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("participantId")
    @JoinColumn(name = "participant_id")
    private Utilisateur participant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("offreId")
    @JoinColumn(name = "offre_id")
    private Offre offre;

    @Column(name = "date_ajout", nullable = false)
    private LocalDateTime dateAjout;

    protected Favori() {
        // constructeur exigé par JPA
    }

    public Favori(Utilisateur participant, Offre offre) {
        this.id = new FavoriId(participant.getId(), offre.getId());
        this.participant = participant;
        this.offre = offre;
    }

    @PrePersist
    void avantCreation() {
        if (dateAjout == null) {
            dateAjout = LocalDateTime.now();
        }
    }

    public FavoriId getId() {
        return id;
    }

    public Utilisateur getParticipant() {
        return participant;
    }

    public Offre getOffre() {
        return offre;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }
}
