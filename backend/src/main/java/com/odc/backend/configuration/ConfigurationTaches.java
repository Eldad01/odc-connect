package com.odc.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Active les traitements en arrière-plan (@Async : envoi des e-mails)
 * et les tâches planifiées (@Scheduled : rappels, expiration, comptes inactifs).
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ConfigurationTaches {
}
