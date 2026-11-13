package com.odc.backend.entite;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/** Opportunité professionnelle publiée par un administrateur (table offre). */
@Entity
@Table(name = "offre")
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false, length = 200)
    private String titre;

    @Column(name = "organisation", nullable = false, length = 200)
    private String organisation;

    /** Liste des valeurs à préciser (I 2) : Barry la transformera en énumération (T 3). */
    @Column(name = "type_contrat", nullable = false, length = 30)
    private String typeContrat;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "competences_requises", nullable = false, columnDefinition = "TEXT")
    private String competencesRequises;

    @Column(name = "contact_candidature", nullable = false, length = 255)
    private String contactCandidature;

    @Column(name = "date_publication", nullable = false, updatable = false)
    private LocalDateTime datePublication;

    /** Facultative en attendant la décision sur I 1. */
    @Column(name = "date_limite")
    private LocalDateTime dateLimite;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 20)
    private StatutOffre statut = StatutOffre.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "auteur_id", nullable = false)
    private Utilisateur auteur;

    @Column(name = "nombre_consultations", nullable = false)
    private int nombreConsultations = 0;

    /** Formations ciblées par l'offre (RG 05), via la table offre_formation. */
    @ManyToMany
    @JoinTable(name = "offre_formation",
            joinColumns = @JoinColumn(name = "offre_id"),
            inverseJoinColumns = @JoinColumn(name = "formation_id"))
    private Set<Formation> formations = new HashSet<>();

    protected Offre() {
        // constructeur exigé par JPA
    }

    public Offre(String titre, String organisation, String typeContrat, String description,
                 String competencesRequises, String contactCandidature, Utilisateur auteur) {
        this.titre = titre;
        this.organisation = organisation;
        this.typeContrat = typeContrat;
        this.description = description;
        this.competencesRequises = competencesRequises;
        this.contactCandidature = contactCandidature;
        this.auteur = auteur;
    }

    @PrePersist
    void avantCreation() {
        if (datePublication == null) {
            datePublication = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDatePublication() {
        return datePublication;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    public String getContactCandidature() {
        return contactCandidature;
    }

    public void setContactCandidature(String contactCandidature) {
        this.contactCandidature = contactCandidature;
    }

    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(LocalDateTime dateLimite) {
        this.dateLimite = dateLimite;
    }

    public StatutOffre getStatut() {
        return statut;
    }

    public void setStatut(StatutOffre statut) {
        this.statut = statut;
    }

    public Utilisateur getAuteur() {
        return auteur;
    }

    public void setAuteur(Utilisateur auteur) {
        this.auteur = auteur;
    }

    public int getNombreConsultations() {
        return nombreConsultations;
    }

    public void setNombreConsultations(int nombreConsultations) {
        this.nombreConsultations = nombreConsultations;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }
}
