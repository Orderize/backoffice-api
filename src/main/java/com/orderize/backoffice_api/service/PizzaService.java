package com.orderize.backoffice_api.service;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.orderize.backoffice_api.external.spoonacular.Spoonacular;
import com.orderize.backoffice_api.external.spoonacular.dto.SpoonacularResponseDto;
import com.orderize.backoffice_api.utils.QuickSortStrategy;
import com.orderize.backoffice_api.utils.SortStrategy;
import com.orderize.backoffice_api.utils.SpoonacularSort;

@Service
public class PizzaService {

    @Value("${external-spoonacular.api.key}")
    private String apiKey;

    @Autowired
    private Spoonacular spoonacular;

    public List<SpoonacularResponseDto> getAllPizzas() {
        List<SpoonacularResponseDto> spoonacularResponseDtos = spoonacular.getRecipes(apiKey, "", 100).results();

        SortStrategy<SpoonacularResponseDto> sortStrategy = new SpoonacularSort(spoonacularResponseDtos);

        List<SpoonacularResponseDto> sortedSpoonacularResponseDto = Arrays.asList(sortStrategy.sort());

        String ids = sortedSpoonacularResponseDto.stream().map(sortedSpoonacularRes -> String.valueOf((long) sortedSpoonacularRes.id())).collect(Collectors.joining(","));
        
        spoonacularResponseDtos = spoonacular.getReceipeInformationById(apiKey, ids, true);

        return spoonacularResponseDtos;
    }
    
}
