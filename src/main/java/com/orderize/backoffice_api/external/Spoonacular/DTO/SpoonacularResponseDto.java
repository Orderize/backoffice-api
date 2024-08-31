package com.orderize.backoffice_api.external.spoonacular.dto;

import java.util.List;

import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SpoonacularResponseDto(
    Long id,
    
    String title,
    
    Double pricePerServing,

    Double readyInMinutes,

    List<IngredientResponseDto> extendedIngredients,

    List<String> occasions,

    String summary
) {}
