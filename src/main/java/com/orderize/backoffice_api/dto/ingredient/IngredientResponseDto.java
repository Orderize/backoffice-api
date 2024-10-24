package com.orderize.backoffice_api.dto.ingredient;

public record IngredientResponseDto(
    Long id,
    String name,
    Double paid
) {}
