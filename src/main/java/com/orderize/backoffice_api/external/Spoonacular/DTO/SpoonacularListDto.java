package com.orderize.backoffice_api.external.spoonacular.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
    
public record SpoonacularListDto(
    @NotNull
    List<SpoonacularResponseDto> results
) {}
