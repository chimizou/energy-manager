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
    MarketSegment marketSegment;
    LocalDate day;
    TimeBloc daySlot;
    double minPrice;
    double quantity;
}
