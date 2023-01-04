package fr.energy.manager.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public enum TimeBloc {
  MIDNIGHT_TO_THREE_AM(LocalTime.of(0, 0, 0, 1), LocalTime.of(3, 0)),
  THREE_AM_TO_SIX_AM(LocalTime.of(3, 0, 0, 1), LocalTime.of(6, 0)),
  SIX_AM_TO_NINE_AM(LocalTime.of(6, 0, 0, 1), LocalTime.of(9, 0)),
  NINE_AM_TO_NOON(LocalTime.of(9, 0, 0, 1), LocalTime.of(12, 0)),
  NOON_TO_THREE_PM(LocalTime.of(12, 0, 0, 1), LocalTime.of(15, 0)),
  THREE_PM_TO_SIX_PM(LocalTime.of(15, 0, 0, 1), LocalTime.of(18, 0)),
  SIX_PM_TO_NINE_PM(LocalTime.of(18, 0, 0, 1), LocalTime.of(21, 0)),
  NINE_PM_TO_MIDNIGHT(LocalTime.of(21, 0, 0, 1), LocalTime.of(0, 0));

  private final LocalTime start;
  private final LocalTime end;
}
