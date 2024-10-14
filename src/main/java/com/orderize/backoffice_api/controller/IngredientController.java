package com.orderize.backoffice_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderize.backoffice_api.dto.ingredient.IngredientRequestDto;
import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;
import com.orderize.backoffice_api.service.IngredientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/ingredients", produces = "application/json")
@Tag(name = "/ingredients")
public class IngredientController {

    private final IngredientService service;

    public IngredientController(IngredientService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas os ingredientes",
            method = "GET",
            description = "Retorna uma lista com todos os ingredientes."
    )
    public ResponseEntity<List<IngredientResponseDto>> getAllIngredients() {
        List<IngredientResponseDto> ingredients = service.getAllIngredients();

        if (ingredients.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um ingrediente por id", method = "GET")
    public ResponseEntity<IngredientResponseDto> getIngredientById(
            @PathVariable("id") Long id
    ) {
        IngredientResponseDto ingredientResponseDto = service.getIngredientById(id);
        return ResponseEntity.status(200).body(ingredientResponseDto);
    }

    @PostMapping
    @Operation(summary = "Salva um novo ingrediente", method = "POST")
    public ResponseEntity<IngredientResponseDto> postIngredient(
            @RequestBody @Valid IngredientRequestDto request
    ) {
        IngredientResponseDto savedIngredient = service.saveIngredient(request);
        return ResponseEntity.status(201).body(savedIngredient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um ingrediente existente", method = "PUT")
    public ResponseEntity<IngredientResponseDto> putIngredient(
            @PathVariable("id") Long id,
            @RequestBody @Valid IngredientRequestDto request
    ) {
        IngredientResponseDto updatedIngredient = service.updateIngredient(id, request);
        return ResponseEntity.status(200).body(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um ingrediente existente", method = "DELETE")
    public ResponseEntity<Void> deleteIngredient(
            @PathVariable("id") Long id
    ) {
        service.deleteIngredient(id);
        return ResponseEntity.status(204).build();
    }

}
