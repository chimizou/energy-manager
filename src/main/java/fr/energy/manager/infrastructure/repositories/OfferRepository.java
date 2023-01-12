package fr.energy.manager.infrastructure.repositories;


import fr.energy.manager.infrastructure.control.ApplicationRepository;
import fr.energy.manager.infrastructure.entities.OfferEntity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferRepository extends ApplicationRepository<OfferEntity, UUID> {}
