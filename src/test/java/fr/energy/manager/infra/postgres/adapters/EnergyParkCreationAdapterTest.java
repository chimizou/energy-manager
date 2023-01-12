package fr.energy.manager.infra.postgres.adapters;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.domain.model.ProductionCapacity;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.infrastructure.adapters.EnergyParkCreationAdapter;
import fr.energy.manager.infrastructure.entities.EnergyParkEntity;
import fr.energy.manager.infrastructure.mappers.EnergyParkEntityMapper;
import fr.energy.manager.infrastructure.repositories.EnergyParkRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static fr.energy.manager.domain.model.PowerMarket.PRIMARY_RESERVE;
import static fr.energy.manager.domain.model.ProductionType.SOLAR;
import static fr.energy.manager.domain.model.TimeBloc.NINE_AM_TO_NOON;
import static io.vavr.API.Seq;
import static org.assertj.vavr.api.VavrAssertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnergyParkCreationAdapterTest {

    @InjectMocks
    private EnergyParkCreationAdapter adapter;
    @Mock
    private EnergyParkRepository repository;

    @Test
    void should_save() {
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
        final var entity = EnergyParkEntityMapper.fromDomain(given);

        when(repository.save(any(EnergyParkEntity.class))).thenReturn(entity);

        final var actual = adapter.save(given);

        assertThat(actual).containsRightInstanceOf(EnergyPark.class);
        Assertions.assertThat(actual.get()).usingRecursiveComparison().isEqualTo(given);

        verify(repository).save(any(EnergyParkEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void should_not_save() {
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
        final var repoException = new IllegalArgumentException();

        when(repository.save(any(EnergyParkEntity.class))).thenThrow(repoException);

        final var actual = adapter.save(given);

        assertThat(actual).containsLeftInstanceOf(ValidationError.class);

        verify(repository).save(any(EnergyParkEntity.class));
        verifyNoMoreInteractions(repository);
    }
}
