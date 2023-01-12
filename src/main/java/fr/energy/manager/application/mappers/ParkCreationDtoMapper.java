package fr.energy.manager.application.mappers;

import fr.energy.manager.application.dtos.ParkCreationDto;
import fr.energy.manager.domain.model.EnergyPark;
import io.vavr.collection.List;

import static io.vavr.API.List;
import static io.vavr.API.Option;

public interface ParkCreationDtoMapper {

    static EnergyPark toDomain(ParkCreationDto dto) {
        return EnergyPark.builder()
                .name(dto.getName())
                .productionType(dto.getProductionType())
                .productionCapacities(
                        Option(dto.getProductionCapacities())
                                .map(List::ofAll)
                                .getOrElse(List())
                                .map(ProductionCapacityCreationDtoMapper::toDomain))
                .offers(
                        Option(dto.getOffers())
                                .map(List::ofAll)
                                .getOrElse(List())
                                .map(OfferCreationDtoMapper::toDomain))
                .build();
    }
}
