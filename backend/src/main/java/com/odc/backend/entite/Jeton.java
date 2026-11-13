package com.odc.backend.entite;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Jeton à usage unique : activation (72 h), réinitialisation (1 h) ou rafraîchissement (7 jours).
 * Seule l'empreinte SHA-256 du jeton est enregistrée, jamais le jeton lui-même (CDCT, section 5.3).
 */
@Entity
@Table(name = "jeton")
public class Jeton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TypeJeton type;

    @Column(name = "valeur_hachee", nullable = false, unique = true, length = 64)
    private String valeurHachee;

    @Column(name = "date_expiration", nullable = false)
    private LocalDateTime dateExpiration;

    @Column(name = "utilise", nullable = false)
    private boolean utilise = false;

    protected Jeton() {
        // constructeur exigé par JPA
    }

    public Jeton(Utilisateur utilisateur, TypeJeton type, String valeurHachee, LocalDateTime dateExpiration) {
        this.utilisateur = utilisateur;
        this.type = type;
        this.valeurHachee = valeurHachee;
        this.dateExpiration = dateExpiration;
    }

    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public TypeJeton getType() {
        return type;
    }

    public void setType(TypeJeton type) {
        this.type = type;
    }

    public String getValeurHachee() {
        return valeurHachee;
    }

    public void setValeurHachee(String valeurHachee) {
        this.valeurHachee = valeurHachee;
    }

    public LocalDateTime getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public boolean isUtilise() {
        return utilise;
    }

    public void setUtilise(boolean utilise) {
        this.utilise = utilise;
    }
}
