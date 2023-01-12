package fr.energy.manager.application.mappers;


import fr.energy.manager.application.dtos.OfferCreationDto;
import fr.energy.manager.domain.model.Offer;

public interface OfferCreationDtoMapper {
    static Offer toDomain(OfferCreationDto dto) {
        return Offer.builder()
                .powerMarket(dto.getPowerMarket())
                .day(dto.getDay())
                .timeBloc(dto.getTimeBloc())
                .floorPrice(dto.getFloorPrice())
                .energyQuantity(dto.getEnergyQuantity())
                .build();
    }
}
