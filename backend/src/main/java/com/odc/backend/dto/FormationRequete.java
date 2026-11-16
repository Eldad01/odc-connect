package com.odc.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Données reçues pour créer ou modifier une formation. */
public record FormationRequete(

        @NotBlank(message = "L'intitulé est obligatoire.")
        @Size(max = 150, message = "L'intitulé ne doit pas dépasser 150 caractères.")
        String intitule) {
}
