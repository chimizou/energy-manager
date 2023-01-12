package fr.energy.manager.infrastructure.mappers;


import fr.energy.manager.domain.model.ProductionCapacity;
import fr.energy.manager.infrastructure.entities.ProductionCapacityEntity;

public interface ProductionCapacityEntityMapper {

  static ProductionCapacityEntity fromDomain(ProductionCapacity dom) {
    return ProductionCapacityEntity.builder().capacity(dom.getCapacity()).day(dom.getDay()).build();
  }

  static ProductionCapacity toDomain(ProductionCapacityEntity entity) {
    return ProductionCapacity.builder().capacity(entity.getCapacity()).day(entity.getDay()).build();
  }
}
