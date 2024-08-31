package com.orderize.backoffice_api.dto.pizza;

import java.util.List;

import com.orderize.backoffice_api.dto.ingredient.IngredientRequestDto;

public record PizzaRequestDto(
    Long id,
    String name,
    Double price,
    Double estimatedTimeFinishing,
    String image,
    List<IngredientRequestDto> ingredients
) {
    public PizzaRequestDto(String name, Double price, Double estimatedTimeFinishing, String image, List<IngredientRequestDto> ingredients) {
        this(null, name, price, estimatedTimeFinishing, image, ingredients);
    }
}
