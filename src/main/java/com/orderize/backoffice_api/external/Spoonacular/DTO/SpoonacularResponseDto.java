package com.orderize.backoffice_api.external.Spoonacular.DTO;

import java.util.List;

import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;

public record SpoonacularResponseDto(
    Long id,
    
    String title,
    
    Double pricePerServing,

    Double readyInMinutes,

    List<IngredientResponseDto> extendedIngredients,

    List<String> occasions,

    String summary
) {}
