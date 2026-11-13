package com.odc.backend.entite;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Formation suivie à l'ODC (table formation). */
@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intitule", nullable = false, unique = true, length = 150)
    private String intitule;

    protected Formation() {
        // constructeur exigé par JPA
    }

    public Formation(String intitule) {
        this.intitule = intitule;
    }

    public Long getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
}
