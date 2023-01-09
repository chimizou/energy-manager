package fr.energy.manager.domain.services;


import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.ports.api.EnergyParkCreationApi;
import fr.energy.manager.domain.ports.spi.EnergyParkCreationSpi;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.domain.validations.EnergyParkValidator;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EnergyParkCreationService implements EnergyParkCreationApi {

  private final EnergyParkCreationSpi spi;

  @Override
  public Either<ValidationError, EnergyPark> create(EnergyPark energyPark) {
    return EnergyParkValidator.validate(energyPark)
        .toEither()
        .flatMap(spi::save)
        .peek(saved -> log.info("Successfully saved park named {}", saved.getName()))
        .peekLeft(
            error ->
                log.error(
                    "An error occurred while trying to save a new park... \n Error details : [{}]",
                    error));
  }
}
