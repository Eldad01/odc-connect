package com.odc.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Documentation Swagger de l'API : http://localhost:8080/swagger-ui.html
 * Le bouton « Authorize » permettra de coller un jeton JWT.
 */
@Configuration
public class ConfigurationOpenApi {

    @Bean
    public OpenAPI documentationApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ODC Connect API")
                        .description("API de la plateforme ODC Connect")
                        .version("1.0"))
                .components(new Components().addSecuritySchemes("jeton",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("jeton"));
    }
}
