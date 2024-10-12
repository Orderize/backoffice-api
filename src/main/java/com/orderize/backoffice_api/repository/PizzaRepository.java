package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    boolean existsByName(String name);
}
