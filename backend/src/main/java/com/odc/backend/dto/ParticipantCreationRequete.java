
package com.odc.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Données reçues pour inscrire un participant. */
public record ParticipantCreationRequete(

        @NotBlank(message = "Le nom est obligatoire.")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères.")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire.")
        @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères.")
        String prenom,

        @NotBlank(message = "L'adresse e-mail est obligatoire.")
        @Email(message = "L'adresse e-mail est invalide.")
        @Size(max = 255)
        String email,

        @NotNull(message = "La formation est obligatoire.")
        Long formationId) {
}
