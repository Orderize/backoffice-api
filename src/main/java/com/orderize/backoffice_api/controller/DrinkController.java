package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.drink.DrinkRequestDto;
import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.service.DrinkService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.model.Drink;

@RestController
@RequestMapping(value = "/drinks", produces = {"application/json"})
@Tag(name = "/drinks")
public class DrinkController {

    private final DrinkService service;
    private final DrinkToDrinkResponse mapperEntityToResponse;

    public DrinkController(DrinkService service, DrinkToDrinkResponse mapperEntityToResponse) {
        this.service = service;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas as bebidas",
            method = "GET",
            description = "Pode receber os request param opcionais: [name, milimeters]" +
                    " e filtra o resultado com base nos request param " +
                    "passados, caso nenhum seja passado retorna uma list com todas as bebidas."
    )
    public ResponseEntity<List<DrinkResponseDto>> getAllDrinks(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "milimeters", required = false) Integer milimeters
    ) {
        List<DrinkResponseDto> drinks = service.getAllDrinks(name, milimeters);

        if (drinks.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(drinks);
        }
    }

    @GetMapping("/pop")
    @Operation(summary = "Busca todos os drinks por popularidade", method = "GET")
    public ResponseEntity<List<DrinkResponseDto>> getAllDrinksByPop(@RequestParam String value) {
        List<Drink> drinks = service.getAllDrinksByPop(value);
        
        if (drinks.isEmpty()) {
            return ResponseEntity.status(204).build();
        } 
        
        return ResponseEntity.status(200).body(drinks.stream().map(it ->
                mapperEntityToResponse.map(it)).toList());
    }


    @PostMapping
    @Operation(
            summary = "Salva uma nova bebida",
            method = "POST"
    )
    public ResponseEntity<DrinkResponseDto> saveDrink(
            @RequestBody @Valid DrinkRequestDto request
    ) {
        DrinkResponseDto saved = service.saveDrink(request);

        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza uma bebida",
            method = "PUT",
            description = "Recebe o id da entidade a ser atualizada pela uri e o DTO de request pelo body, e retorna" +
                    "a entidade atualizada"
    )
    public ResponseEntity<DrinkResponseDto> updateDrink(
            @PathVariable("id") Long id,
            @RequestBody @Valid DrinkRequestDto request
    ) {
        DrinkResponseDto updated = service.updateDrink(id, request);

        return ResponseEntity.status(200).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma bebida",
            method = "DELETE",
            description = "Recebe um id por uri a bebida correspondente"
    )
    public ResponseEntity<Void> deleteDrink(
            @PathVariable("id") Long id
    ) {
        service.deleteDrink(id);

        return ResponseEntity.status(204).build();
    }

}