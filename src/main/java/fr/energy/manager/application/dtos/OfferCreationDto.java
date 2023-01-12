package fr.energy.manager.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.energy.manager.domain.model.PowerMarket;
import fr.energy.manager.domain.model.TimeBloc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OfferCreationDto {

  @JsonProperty(value = "powerMarket", required = true)
  PowerMarket powerMarket;

  @JsonProperty(value = "day", required = true)
  LocalDate day;

  @JsonProperty(value = "timeBloc", required = true)
  TimeBloc timeBloc;

  @JsonProperty(value = "floorPrice", required = true)
  double floorPrice;

  @JsonProperty(value = "energyQuantity", required = true)
  double energyQuantity;
}
