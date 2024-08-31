package com.orderize.backoffice_api.dto.pizza;

import java.util.List;

import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;

public record PizzaResponseDto(
    Long id,
    String name,
    Double price,
    Double estimatedTimeFinishing,
    String image,
    List<IngredientResponseDto> ingredients
) {
}
