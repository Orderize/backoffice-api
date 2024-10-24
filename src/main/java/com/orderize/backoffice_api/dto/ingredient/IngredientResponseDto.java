package com.orderize.backoffice_api.dto.ingredient;

import java.math.BigDecimal;

public record IngredientResponseDto(
    Long id,
    String name,
    BigDecimal paid
) {}
