package com.odc.backend.controleur;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odc.backend.dto.FormationReponse;
import com.odc.backend.dto.FormationRequete;
import com.odc.backend.service.ServiceFormation;

import jakarta.validation.Valid;

/**
 * Endpoints /formations (US 03, US 04).
 * GET : tout utilisateur connecté. POST, PUT, DELETE : ADMIN.
 * Les droits sont appliqués par la configuration de sécurité (partie d'Isabel).
 */
@RestController
@RequestMapping("/formations")
public class ControleurFormation {

    private final ServiceFormation serviceFormation;

    public ControleurFormation(ServiceFormation serviceFormation) {
        this.serviceFormation = serviceFormation;
    }

    /** Liste complète, triée par intitulé (liste courte, sert au filtre). */
    @GetMapping
    public List<FormationReponse> lister() {
        return serviceFormation.lister();
    }

    @PostMapping
    public ResponseEntity<FormationReponse> creer(@Valid @RequestBody FormationRequete requete) {
        FormationReponse creee = serviceFormation.creer(requete);
        return ResponseEntity.created(URI.create("/formations/" + creee.id())).body(creee);
    }

    @PutMapping("/{id}")
    public FormationReponse modifier(@PathVariable Long id, @Valid @RequestBody FormationRequete requete) {
        return serviceFormation.modifier(id, requete);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        serviceFormation.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}
