package fr.energy.manager.infrastructure.mappers;


import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.infrastructure.entities.OfferEntity;

public interface OfferEntityMapper {

  static OfferEntity fromDomain(Offer dom) {
    return OfferEntity.builder()
        .id(dom.getId())
        .powerMarket(dom.getPowerMarket())
        .day(dom.getDay())
        .timeBloc(dom.getTimeBloc())
        .floorPrice(dom.getFloorPrice())
        .energyQuantity(dom.getEnergyQuantity())
        .build();
  }

  static Offer toDomain(OfferEntity entity) {
    return Offer.builder()
        .id(entity.getId())
        .powerMarket(entity.getPowerMarket())
        .day(entity.getDay())
        .timeBloc(entity.getTimeBloc())
        .floorPrice(entity.getFloorPrice())
        .energyQuantity(entity.getEnergyQuantity())
        .build();
  }
}
