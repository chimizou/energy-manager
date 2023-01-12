package fr.energy.manager.application.mappers;

import fr.energy.manager.application.dtos.OfferCreationDto;
import fr.energy.manager.domain.model.Offer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.TimeBloc.NINE_AM_TO_NOON;
import static org.assertj.core.api.Assertions.assertThat;


class OfferCreationDtoMapperTest {

    @Test
    void should_map() {
        final var given =
                OfferCreationDto.builder()
                        .powerMarket(PRIMARY_RESERVE)
                        .day(LocalDate.of(2022, 6, 5))
                        .timeBloc(NINE_AM_TO_NOON)
                        .floorPrice(50.12)
                        .energyQuantity(25)
                        .build();

        final var actual = OfferCreationDtoMapper.toDomain(given);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(
                        Offer.builder()
                                .powerMarket(PRIMARY_RESERVE)
                                .day(LocalDate.of(2022, 6, 5))
                                .timeBloc(NINE_AM_TO_NOON)
                                .floorPrice(50.12)
                                .energyQuantity(25)
                                .build());
        assertThat(actual.getId()).isNotNull();
    }
}
