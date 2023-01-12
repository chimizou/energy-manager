package fr.energy.manager.infrastructure.adapters;


import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.ports.spi.EnergyParkCreationSpi;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.infrastructure.entities.EnergyParkEntity;
import fr.energy.manager.infrastructure.mappers.EnergyParkEntityMapper;
import fr.energy.manager.infrastructure.repositories.EnergyParkRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.API.Try;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnergyParkCreationAdapter implements EnergyParkCreationSpi {

    private final EnergyParkRepository repository;

    @Override
    @Transactional
    public Either<ValidationError, EnergyPark> save(EnergyPark park) {
        return Try(() -> EnergyParkEntityMapper.fromDomain(park))
                .mapTry(this::setParentForEachChild)
                .mapTry(repository::save)
                .toEither()
                .map(EnergyParkEntityMapper::toDomain)
                .mapLeft(throwable -> ValidationError.of(throwable.getMessage(), park));
    }

    private EnergyParkEntity setParentForEachChild(EnergyParkEntity entity) {
        entity.getOffers().forEach(offer -> offer.setEnergyPark(entity));
        entity.getProductionCapacities().forEach(capacity -> capacity.setProductionPark(entity));
        return entity;
    }
}
