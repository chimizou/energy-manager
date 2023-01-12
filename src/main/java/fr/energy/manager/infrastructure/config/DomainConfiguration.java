package fr.energy.manager.infrastructure.config;

import fr.energy.manager.domain.ports.api.EnergyParkCreationApi;
import fr.energy.manager.domain.ports.spi.EnergyParkCreationSpi;
import fr.energy.manager.domain.services.EnergyParkCreationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

  @Bean
  public EnergyParkCreationApi productionParkCreationService(EnergyParkCreationSpi parkCreationSpi) {
    return new EnergyParkCreationService(parkCreationSpi);
  }
}
