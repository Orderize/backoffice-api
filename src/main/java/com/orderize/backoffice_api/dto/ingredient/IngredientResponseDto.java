package com.orderize.backoffice_api.dto.ingredient;

import com.orderize.backoffice_api.dto.measure.MeasureResponseDto;

public record IngredientResponseDto(
    Long id,
    String name,
    Double amount,
    String unit,
    MeasureResponseDto measures,
    Double paid
) {
}
