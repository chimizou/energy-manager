package fr.energy.manager.infrastructure.control;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationRepository<T, ID>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {}
