
package com.odc.backend.controleur;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odc.backend.dto.ParticipantCreationRequete;
import com.odc.backend.service.ServiceParticipant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/participants")
public class ControleurParticipant {

    private final ServiceParticipant serviceParticipant;

    public ControleurParticipant(ServiceParticipant serviceParticipant) {
        this.serviceParticipant = serviceParticipant;
    }

    /**
     * Inscrire un participant et lui envoyer une invitation.
     */
    @PostMapping
    public ResponseEntity<Void> inscrire(
            @Valid @RequestBody ParticipantCreationRequete requete) {

        Long id = serviceParticipant.inscrire(requete);

        return ResponseEntity
                .created(URI.create("/participants/" + id))
                .build();
    }
}
