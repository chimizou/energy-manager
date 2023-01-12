package fr.energy.manager.application.mappers;

import fr.energy.manager.application.dtos.ProductionCapacityCreationDto;
import fr.energy.manager.domain.model.ProductionCapacity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductionCapacityCreationDtoMapperTest {

    @Test
    void should_map() {
        final var day = LocalDate.of(2022, 6, 5);
        final var given = ProductionCapacityCreationDto.builder().capacity(50).day(day).build();

        final var actual = ProductionCapacityCreationDtoMapper.toDomain(given);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(ProductionCapacity.builder().capacity(50).day(day).build());
    }
}
