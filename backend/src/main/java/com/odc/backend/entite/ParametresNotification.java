package com.odc.backend.entite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/** Choix du participant pour les notifications et les rappels (US 05). Une ligne par participant. */
@Entity
@Table(name = "parametres_notification")
public class ParametresNotification {

    @Id
    @Column(name = "participant_id")
    private Long participantId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "participant_id")
    private Utilisateur participant;

    @Column(name = "notifications_actives", nullable = false)
    private boolean notificationsActives = true;

    @Column(name = "rappels_actifs", nullable = false)
    private boolean rappelsActifs = true;

    protected ParametresNotification() {
        // constructeur exigé par JPA
    }

    /** Crée les paramètres par défaut : notifications et rappels activés. */
    public ParametresNotification(Utilisateur participant) {
        this.participant = participant;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public Utilisateur getParticipant() {
        return participant;
    }

    public boolean isNotificationsActives() {
        return notificationsActives;
    }

    public void setNotificationsActives(boolean notificationsActives) {
        this.notificationsActives = notificationsActives;
    }

    public boolean isRappelsActifs() {
        return rappelsActifs;
    }

    public void setRappelsActifs(boolean rappelsActifs) {
        this.rappelsActifs = rappelsActifs;
    }
}
