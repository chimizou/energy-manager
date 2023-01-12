package fr.energy.manager.domain.services;


import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.domain.model.ProductionCapacity;
import fr.energy.manager.domain.ports.spi.EnergyParkCreationSpi;
import fr.energy.manager.domain.validations.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.ProductionType.SOLAR;
import static fr.energy.manager.domain.model.TimeBloc.NINE_AM_TO_NOON;
import static io.vavr.API.*;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkCreationServiceTest {

    @InjectMocks
    private EnergyParkCreationService service;

    @Mock
    private EnergyParkCreationSpi spi;

    @Test
    void should_validate_and_create_new_park() {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                EnergyPark.builder()
                        .name("name")
                        .productionType(SOLAR)
                        .productionCapacities(Seq(ProductionCapacity.builder().capacity(40).day(day).build()))
                        .offers(
                                Seq(
                                        Offer.builder()
                                                .powerMarket(PRIMARY_RESERVE)
                                                .day(day)
                                                .timeBloc(NINE_AM_TO_NOON)
                                                .floorPrice(50.12)
                                                .energyQuantity(25)
                                                .build()))
                        .build();

        when(spi.save(given)).thenReturn(Right(given));

        final var actual = service.create(given);

        assertThat(actual).containsRightSame(given);
        verify(spi).save(given);
        verifyNoMoreInteractions(spi);
    }

    @Test
    void should_not_create_park_if_validation_failed() {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                EnergyPark.builder()
                        .name(null)
                        .productionType(null)
                        .productionCapacities(
                                Seq(
                                        ProductionCapacity.builder().capacity(40).day(day).build(),
                                        ProductionCapacity.builder().capacity(40).day(day).build()))
                        .build();

        final var actual = service.create(given);

        assertThat(actual).containsLeftInstanceOf(ValidationError.class);
        verifyNoInteractions(spi);
    }

    @Test
    void should_validate_but_dont_create_new_park_on_spi_error() {
        final var day = LocalDate.of(2022, 6, 5);
        final var given =
                EnergyPark.builder()
                        .name("name")
                        .productionType(SOLAR)
                        .productionCapacities(Seq(ProductionCapacity.builder().capacity(40).day(day).build()))
                        .offers(
                                Seq(
                                        Offer.builder()
                                                .powerMarket(PRIMARY_RESERVE)
                                                .day(day)
                                                .timeBloc(NINE_AM_TO_NOON)
                                                .floorPrice(50.12)
                                                .energyQuantity(25)
                                                .build()))
                        .build();

        when(spi.save(given)).thenReturn(Left(ValidationError.of("message", null)));

        final var actual = service.create(given);

        assertThat(actual).containsLeftInstanceOf(ValidationError.class);
        verify(spi).save(given);
        verifyNoMoreInteractions(spi);
    }
}
