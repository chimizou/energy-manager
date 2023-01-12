package fr.energy.manager.domain.validations;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.domain.model.ProductionCapacity;
import io.vavr.collection.Seq;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.ProductionType.SOLAR;
import static fr.energy.manager.domain.model.TimeBloc.NOON_TO_THREE_PM;
import static io.vavr.API.Seq;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

class ParkValidatorTest {

    @ParameterizedTest
    @MethodSource("provideValidParks")
    void should_be_valid(EnergyPark validPark) {
        final var actual = EnergyParkValidator.validate(validPark);
        assertThat(actual).containsValidSame(validPark);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidParks")
    void should_not_be_valid(EnergyPark invalidPark) {
        final var actual = EnergyParkValidator.validate(invalidPark);
        assertThat(actual).containsInvalidInstanceOf(ValidationError.class);
    }

    private static Stream<Arguments> provideValidParks() {
        final var validId = UUID.randomUUID();
        final var validName = "validName";
        final var validType = SOLAR;
        final Seq<ProductionCapacity> validEmptyCapacities = Seq();
        final Seq<Offer> validEmptyOffers = Seq();

        final var day = LocalDate.of(2022, 6, 5);
        final var capacity = ProductionCapacity.builder().day(day).capacity(50).build();
        final var offer =
                Offer.builder()
                        .id(validId)
                        .powerMarket(PRIMARY_RESERVE)
                        .day(day)
                        .timeBloc(NOON_TO_THREE_PM)
                        .floorPrice(40)
                        .energyQuantity(50)
                        .build();

        return Stream.of(
                Arguments.of(
                        EnergyPark.builder()
                                .id(validId)
                                .name(validName)
                                .productionType(validType)
                                .productionCapacities(validEmptyCapacities)
                                .offers(validEmptyOffers)
                                .build()),
                Arguments.of(
                        EnergyPark.builder()
                                .id(validId)
                                .name(validName)
                                .productionType(validType)
                                .productionCapacities(Seq(capacity))
                                .offers(Seq(offer))
                                .build()));
    }

    private static Stream<Arguments> provideInvalidParks() {
        final var day = LocalDate.of(2022, 6, 5);
        final var capacity = ProductionCapacity.builder().day(day).capacity(50).build();
        final var offer =
                Offer.builder()
                        .powerMarket(PRIMARY_RESERVE)
                        .day(day)
                        .timeBloc(NOON_TO_THREE_PM)
                        .floorPrice(40)
                        .energyQuantity(50)
                        .build();

        return Stream.of(
                Arguments.of(
                        EnergyPark.builder() // park with invalid fields
                                .id(null)
                                .name(null)
                                .productionType(null)
                                .build()),
                Arguments.of(EnergyPark.builder().build()), // also park with invalid fields
                Arguments.of(
                        EnergyPark.builder() // park with valid fields but invalid capacities and valid offers
                                .id(UUID.randomUUID())
                                .name("name")
                                .productionType(SOLAR)
                                .productionCapacities(Seq(capacity, capacity))
                                .offers(Seq(offer))
                                .build()),
                Arguments.of(
                        EnergyPark.builder() // park with valid fields and capacities but invalid offers
                                .id(UUID.randomUUID())
                                .name("name")
                                .productionType(SOLAR)
                                .productionCapacities(Seq(capacity))
                                .offers(Seq(offer, offer))
                                .build()));
    }
}
