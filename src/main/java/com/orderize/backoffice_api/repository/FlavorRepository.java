package com.orderize.backoffice_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.orderize.backoffice_api.model.Flavor;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long>{
    Boolean existsByName(String name);
   
    @Query(value = "SELECT f.*, pf.quant FROM flavor f LEFT JOIN (SELECT flavor_id, COUNT(flavor_id) quant FROM pizza_flavor GROUP BY flavor_id) pf ON f.id = pf.flavor_id ORDER BY pf.quant DESC", nativeQuery = true)
    List<Flavor> findByPopularity();
}

