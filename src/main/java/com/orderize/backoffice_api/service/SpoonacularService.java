package com.orderize.backoffice_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.external.Spoonacular.Spoonacular;
import com.orderize.backoffice_api.external.Spoonacular.DTO.SpoonacularResponseDto;
import com.orderize.backoffice_api.utils.List__c;

@Service
public class SpoonacularService {

    @Value("${external-spoonacular.api.key}")
    private String apiKey;

    @Autowired
    private Spoonacular spoonacular;

    // falta colocar a lista customizada

    public List<SpoonacularResponseDto> getAllPizzas() {
        List__c<SpoonacularResponseDto> spoonacularResponseDtos = new List__c<>(spoonacular.getRecipes(apiKey, "", 100).results());
        Comparator<SpoonacularResponseDto> titleComparator = Comparator.comparing(SpoonacularResponseDto::title);
        spoonacularResponseDtos.sort(titleComparator);

        List<String> listId = new ArrayList<>();
        for (int i = 0; i < spoonacularResponseDtos.size(); i++) {
            SpoonacularResponseDto spoonacularDto = spoonacularResponseDtos.get(i);
            String id = String.valueOf(spoonacularDto.id());
            listId.add(id);
        }
        String ids = listId.stream().collect(Collectors.joining(","));
        
        spoonacularResponseDtos = new List__c<>(spoonacular.getReceipeInformationById(apiKey, ids, true)); 

        return spoonacularResponseDtos.toList();
    }
    
}