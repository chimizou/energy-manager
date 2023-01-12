package fr.energy.manager.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "fr.energy.manager.infrastructure.entities")
@EnableJpaRepositories(basePackages = "fr.energy.manager.infrastructure.repositories")
public class JpaConfiguration {}
