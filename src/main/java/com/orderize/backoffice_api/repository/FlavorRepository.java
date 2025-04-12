package com.orderize.backoffice_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderize.backoffice_api.model.Flavor;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long>{
    Boolean existsByName(String name);
   
    @Query(
        "SELECT f FROM Flavor f " +
        "LEFT JOIN f.pizzas p " + 
        "GROUP BY f " +
        "ORDER BY COUNT(p) DESC")
    List<Flavor> findByPopularity();
}

