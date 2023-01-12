package fr.energy.manager.infrastructure.mappers;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.infrastructure.entities.EnergyParkEntity;
import io.vavr.collection.List;
import io.vavr.collection.Seq;

import java.util.ArrayList;

import static io.vavr.API.List;
import static io.vavr.API.Option;

public interface EnergyParkEntityMapper {

  static EnergyParkEntity fromDomain(EnergyPark dom) {
    return EnergyParkEntity.builder()
        .id(dom.getId())
        .name(dom.getName())
        .productionType(dom.getProductionType())
        .productionCapacities(
            Option(dom.getProductionCapacities())
                .map(capacities -> capacities.map(ProductionCapacityEntityMapper::fromDomain))
                .map(Seq::asJavaMutable)
                .getOrElse(new ArrayList<>()))
        .offers(
            Option(dom.getOffers())
                .map(offers -> offers.map(OfferEntityMapper::fromDomain))
                .map(Seq::asJavaMutable)
                .getOrElse(new ArrayList<>()))
        .build();
  }

  static EnergyPark toDomain(EnergyParkEntity entity) {
    return EnergyPark.builder()
        .id(entity.getId())
        .name(entity.getName())
        .productionType(entity.getProductionType())
        .productionCapacities(
            Option(entity.getProductionCapacities())
                .map(List::ofAll)
                .getOrElse(List())
                .map(ProductionCapacityEntityMapper::toDomain))
        .offers(
            Option(entity.getOffers())
                .map(List::ofAll)
                .getOrElse(List())
                .map(OfferEntityMapper::toDomain))
        .build();
  }
}
