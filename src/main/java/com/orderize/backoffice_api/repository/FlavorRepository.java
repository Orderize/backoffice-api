package com.orderize.backoffice_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderize.backoffice_api.model.Flavor;

public interface FlavorRepository extends JpaRepository<Flavor, Long>{
    Boolean existsByName(String name);
}
