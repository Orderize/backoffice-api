package com.orderize.backoffice_api.mapper.ingredient;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.ingredient.IngredientRequestDto;
import com.orderize.backoffice_api.model.Ingredient;

@Component
public class IngredientRequestDtoToIngredient {

    public Ingredient map(IngredientRequestDto ingredientRequestDto) {
        return new Ingredient(
            ingredientRequestDto.name(),
            ingredientRequestDto.paid()
        );
    }
}
