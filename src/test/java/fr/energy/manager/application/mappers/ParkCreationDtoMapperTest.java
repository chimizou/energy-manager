package fr.energy.manager.application.mappers;


import fr.energy.manager.application.dtos.OfferCreationDto;
import fr.energy.manager.application.dtos.ParkCreationDto;
import fr.energy.manager.application.dtos.ProductionCapacityCreationDto;
import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.domain.model.ProductionCapacity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.ProductionType.SOLAR;
import static fr.energy.manager.domain.model.TimeBloc.NINE_AM_TO_NOON;
import static io.vavr.API.Seq;
import static org.assertj.core.api.Assertions.assertThat;

class ParkCreationDtoMapperTest {

    @Test
    void should_map() {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                ParkCreationDto.builder()
                        .name("name")
                        .productionType(SOLAR)
                        .productionCapacities(
                                java.util.List.of(
                                        ProductionCapacityCreationDto.builder().capacity(40).day(day).build()))
                        .offers(
                                java.util.List.of(
                                        OfferCreationDto.builder()
                                                .powerMarket(PRIMARY_RESERVE)
                                                .day(day)
                                                .timeBloc(NINE_AM_TO_NOON)
                                                .floorPrice(50.12)
                                                .energyQuantity(25)
                                                .build()))
                        .build();

        final var actual = ParkCreationDtoMapper.toDomain(given);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id", "offers.id")
                .isEqualTo(
                        EnergyPark.builder()
                                .name("name")
                                .productionType(SOLAR)
                                .productionCapacities(
                                        Seq(ProductionCapacity.builder().capacity(40).day(day).build()))
                                .offers(
                                        Seq(
                                                Offer.builder()
                                                        .powerMarket(PRIMARY_RESERVE)
                                                        .day(day)
                                                        .timeBloc(NINE_AM_TO_NOON)
                                                        .floorPrice(50.12)
                                                        .energyQuantity(25)
                                                        .build()))
                                .build());
        assertThat(actual.getId()).isNotNull();
    }
}
