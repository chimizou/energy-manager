package fr.energy.manager.domain.ports.api;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.validations.ValidationError;
import io.vavr.control.Either;

public interface EnergyParkCreationApi {
    Either<ValidationError, EnergyPark> create(EnergyPark energyPark);
}
