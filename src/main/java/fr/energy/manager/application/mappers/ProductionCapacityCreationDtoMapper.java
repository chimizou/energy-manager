package fr.energy.manager.application.mappers;


import fr.energy.manager.application.dtos.ProductionCapacityCreationDto;
import fr.energy.manager.domain.model.ProductionCapacity;

public interface ProductionCapacityCreationDtoMapper {
  static ProductionCapacity toDomain(ProductionCapacityCreationDto dto) {
    return ProductionCapacity.builder().capacity(dto.getCapacity()).day(dto.getDay()).build();
  }
}
