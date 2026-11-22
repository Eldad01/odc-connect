package com.odc.backend.controleur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odc.backend.dto.StatistiquesReponse;
import com.odc.backend.service.ServiceStatistiques;

/**
 * Endpoint GET /statistiques (US 09), réservé à l'ADMIN (RG 12).
 * Le droit ADMIN est appliqué par la configuration de sécurité (partie d'Isabel).
 */
@RestController
@RequestMapping("/statistiques")
public class ControleurStatistiques {

    private final ServiceStatistiques serviceStatistiques;

    public ControleurStatistiques(ServiceStatistiques serviceStatistiques) {
        this.serviceStatistiques = serviceStatistiques;
    }

    @GetMapping
    public StatistiquesReponse afficher() {
        return serviceStatistiques.calculer();
    }
}
