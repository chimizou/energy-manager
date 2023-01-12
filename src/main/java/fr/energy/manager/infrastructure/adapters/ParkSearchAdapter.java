package fr.energy.manager.infrastructure.adapters;

import fr.energy.manager.domain.model.EnergyPark;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.infrastructure.entities.EnergyParkEntity;
import fr.energy.manager.infrastructure.mappers.EnergyParkEntityMapper;
import fr.energy.manager.infrastructure.repositories.EnergyParkRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.API.Try;

@Component
@RequiredArgsConstructor
public class ParkSearchAdapter {

  private final EnergyParkRepository repository;

  @Transactional(readOnly = true)
  public Either<ValidationError, Page<EnergyPark>> search(int page, int size, String query, String sort) {
    final var spec = JpaSpecificationBuilder.<EnergyParkEntity>build(query, sort);
    final var pageRequest = PageRequest.of(page, size);

    return Try(() -> repository.findAll(spec, pageRequest))
        .mapTry(pageResult -> pageResult.map(EnergyParkEntityMapper::toDomain))
        .toEither()
        .mapLeft(throwable -> ValidationError.of(throwable.getMessage(), throwable));
  }
}
