package com.orderize.backoffice_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orderize.backoffice_api.model.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    Boolean existsByName(String name);
}
