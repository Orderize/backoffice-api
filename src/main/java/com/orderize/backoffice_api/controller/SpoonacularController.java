package com.orderize.backoffice_api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderize.backoffice_api.external.Spoonacular.DTO.SpoonacularResponseDto;
import com.orderize.backoffice_api.service.SpoonacularService;



@RestController
@RequestMapping("/spoonacular")
public class SpoonacularController {
    
    @Autowired
    private SpoonacularService pizzaService;

    @GetMapping
    public ResponseEntity<List<SpoonacularResponseDto>> getAllPizzas() {
        List<SpoonacularResponseDto> pizzaList = pizzaService.getAllPizzas();
        if (pizzaList.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(pizzaList);
    }
}
