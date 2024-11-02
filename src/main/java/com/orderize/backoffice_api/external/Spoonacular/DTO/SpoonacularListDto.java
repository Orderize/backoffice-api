package com.orderize.backoffice_api.external.Spoonacular.DTO;

import java.util.List;

import com.orderize.backoffice_api.utils.List__c;

import jakarta.validation.constraints.NotNull;
    
public record SpoonacularListDto(
    @NotNull
    List<SpoonacularResponseDto> results
) {}
