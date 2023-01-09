package fr.energy.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = {"fr.energy.manager"})
public class EnergyManagerApplication {

  public static void main(String[] args) {
    SpringApplication.run(EnergyManagerApplication.class, args);
  }
}
