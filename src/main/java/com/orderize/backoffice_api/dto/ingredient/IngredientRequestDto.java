package com.orderize.backoffice_api.dto.ingredient;

public record IngredientRequestDto(
    Long id,
    String name, 
    Double paid
) {
}
