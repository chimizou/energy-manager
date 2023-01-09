package fr.energy.manager.domain.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PowerMarket {
  PRIMARY_RESERVE,
  SECONDARY_RESERVE,
  QUICK_RESERVE
}
