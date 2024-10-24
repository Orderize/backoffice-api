package com.orderize.backoffice_api.mapper.ingredient;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;
import com.orderize.backoffice_api.model.Ingredient;

@Component
public class IngredientToIngredientResponseDto {

    public IngredientResponseDto map(Ingredient ingredient) {
        return new IngredientResponseDto(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getPaid()
        );
    }

}
