package fr.energy.manager.infrastructure.entities;

import fr.energy.manager.domain.model.PowerMarket;
import fr.energy.manager.domain.model.TimeBloc;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "offer",
    indexes = {@Index(columnList = "day", name = "offer_day_index")})
public class OfferEntity {

  @Id private UUID id;

  @Enumerated(STRING)
  private PowerMarket powerMarket;

  private LocalDate day;

  @Enumerated(STRING)
  private TimeBloc timeBloc;

  private double floorPrice;
  private double energyQuantity;

  @ManyToOne
  @JoinColumn(name = "park_id", insertable = false, updatable = false)
  private EnergyParkEntity energyPark;
}
