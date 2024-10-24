package com.orderize.backoffice_api.dto.ingredient;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequestDto(
    @NotBlank String name,
    @NotNull BigDecimal paid,
    Long flavor
) {
    public IngredientRequestDto(String name, BigDecimal paid) {
        this(name, paid, null);
    }
}
