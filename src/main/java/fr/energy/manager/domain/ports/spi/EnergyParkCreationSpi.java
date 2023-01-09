package fr.energy.manager.domain.ports.spi;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.validations.ValidationError;
import io.vavr.control.Either;

public interface EnergyParkCreationSpi {
  Either<ValidationError, EnergyPark> save(EnergyPark energyPark);
}
