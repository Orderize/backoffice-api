package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.Drink;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

    boolean existsByName(String name);

    @Query( 
        "SELECT d FROM Drink d " +
        "LEFT JOIN d.orders o " +
        "GROUP BY d " + 
        "ORDER BY COUNT(o) DESC")
    List<Drink> findByPopularity();

}
