package fr.energy.manager.infrastructure.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "production_capacity",
    indexes = {@Index(columnList = "day", name = "capacity_day_index")})
public class ProductionCapacityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private double capacity;
  private LocalDate day;

  @ManyToOne
  @JoinColumn(name = "park_id", insertable = false, updatable = false)
  private EnergyParkEntity productionPark;
}
