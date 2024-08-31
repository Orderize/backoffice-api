package com.orderize.backoffice_api.external.spoonacular;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.orderize.backoffice_api.external.spoonacular.dto.SpoonacularListDto;
import com.orderize.backoffice_api.external.spoonacular.dto.SpoonacularResponseDto;

import java.util.List;


/*
 * API utlizada= https://spoonacular.com/
*/

// Usada para criar um cliente HTTP, simplifica a integração com serviços externos
@FeignClient(name = "SpoonacularTokenApi", url="${external-spoonacular.url}")
public interface Spoonacular {
    
    // Retorna informações da pesquisa
    @GetMapping("/recipes/complexSearch")
    public SpoonacularListDto getRecipes(
        @RequestParam String apiKey,
        @RequestParam String query,
        @RequestParam(defaultValue = "10") int number
    );

    // Retorna as informações das pizzas informadas pelos ids
    @GetMapping("/recipes/informationBulk")
    public List<SpoonacularResponseDto> getReceipeInformationById(
        @RequestParam String apiKey,
        @RequestParam String ids,
        @RequestParam(defaultValue = "true") boolean includeNutrition
    );
}
