package com.orderize.backoffice_api.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
    @NotBlank String name,
    @NotNull Double paid,
    Long flavor
) {
    public IngredientRequestDto(String name, Double paid) {
        this(name, paid, null);
    }
}
