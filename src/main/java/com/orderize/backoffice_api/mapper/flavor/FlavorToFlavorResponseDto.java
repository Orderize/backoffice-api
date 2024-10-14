package com.orderize.backoffice_api.mapper.flavor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.mapper.ingredient.IngredientToIngredientResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Ingredient;

@Component
public class FlavorToFlavorResponseDto {

    private final IngredientToIngredientResponseDto ingredientToIngredientResponseDto;

    public FlavorToFlavorResponseDto(IngredientToIngredientResponseDto ingredientToIngredientResponseDto) {
        this.ingredientToIngredientResponseDto = ingredientToIngredientResponseDto;
    }

    public FlavorResponseDto map(Flavor flavor) {
        return new FlavorResponseDto(
                flavor.getId(),
                flavor.getName(),
                flavor.getDescription(),
                flavor.getRegistered(),
                flavor.getIngredients().stream().map(it -> ingredientToIngredientResponseDto.map(it)).collect(Collectors.toList())
        );
    }

    public FlavorResponseDto map(Flavor flavor, List<Ingredient> ingredients) {
        return new FlavorResponseDto(
                flavor.getId(),
                flavor.getName(),
                flavor.getDescription(),
                flavor.getRegistered(),
                ingredients.stream().map(it -> ingredientToIngredientResponseDto.map(it)).collect(Collectors.toList())
        );
    }
}
