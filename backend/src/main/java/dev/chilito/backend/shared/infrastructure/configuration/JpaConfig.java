package dev.chilito.backend.shared.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // This is to enable the audit fields
public class JpaConfig {

}
