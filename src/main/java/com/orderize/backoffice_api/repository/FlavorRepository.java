package com.orderize.backoffice_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderize.backoffice_api.model.Flavor;

@Repository
public interface FlavorRepository extends JpaRepository<Flavor, Long>{
    Boolean existsByName(String name);
}
