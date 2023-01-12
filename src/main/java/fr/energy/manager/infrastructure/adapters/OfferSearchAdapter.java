package fr.energy.manager.infrastructure.adapters;

import fr.energy.manager.domain.model.Offer;
import fr.energy.manager.domain.validations.ValidationError;
import fr.energy.manager.infrastructure.entities.OfferEntity;
import fr.energy.manager.infrastructure.mappers.OfferEntityMapper;
import fr.energy.manager.infrastructure.repositories.OfferRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static io.vavr.API.Try;

@Component
@RequiredArgsConstructor
public class OfferSearchAdapter {

  private final OfferRepository repository;

  @Transactional(readOnly = true)
  public Either<ValidationError, Page<Offer>> search(int page, int size, String query, String sort) {
    final var spec = JpaSpecificationBuilder.<OfferEntity>build(query, sort);
    final var pageRequest = PageRequest.of(page, size);

    return Try(() -> repository.findAll(spec, pageRequest))
        .mapTry(pageResult -> pageResult.map(OfferEntityMapper::toDomain))
        .toEither()
        .mapLeft(throwable -> ValidationError.of(throwable.getMessage(), throwable));
  }
}
