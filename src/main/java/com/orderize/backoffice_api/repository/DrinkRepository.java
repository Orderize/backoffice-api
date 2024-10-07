package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    boolean existsByName(String name);

}
