package fr.energy.manager.domain.model;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class Offer {
    @Default UUID id = UUID.randomUUID();
    PowerMarket powerMarket;
    LocalDate day;
    TimeBloc timeBloc;
    double floorPrice;
    double energyQuantity;
}
