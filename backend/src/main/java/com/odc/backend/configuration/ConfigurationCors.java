package com.odc.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Autorise le front Angular à appeler l'API.
 * L'origine se règle dans .env (CORS_ORIGINES), par défaut http://localhost:4200.
 * Isabel devra activer http.cors() dans la configuration de sécurité.
 */
@Configuration
public class ConfigurationCors implements WebMvcConfigurer {

    private final String[] originesAutorisees;

    public ConfigurationCors(@Value("${app.cors.origines-autorisees}") String[] originesAutorisees) {
        this.originesAutorisees = originesAutorisees;
    }

    @Override
    public void addCorsMappings(CorsRegistry registre) {
        registre.addMapping("/**")
                .allowedOrigins(originesAutorisees)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
