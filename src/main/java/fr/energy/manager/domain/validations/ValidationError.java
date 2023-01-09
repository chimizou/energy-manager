package fr.energy.manager.domain.validations;

import lombok.Value;

@Value(staticConstructor = "of")
public class ValidationError {
  String message;
  Object invalidValue;
}
