package com.orderize.backoffice_api.dto.flavor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;

public record FlavorResponseDto (
    Long id,
    String name,
    String description,
    BigDecimal price,
    LocalDate registered,
    List<IngredientResponseDto> ingredients
) {

}
