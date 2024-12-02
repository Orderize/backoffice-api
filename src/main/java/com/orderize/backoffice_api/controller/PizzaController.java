package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.mapper.pizza.PizzaRequestToPizza;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponse;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.service.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pizzas", produces = "application/json")
@Tag(name = "/pizzas")
public class PizzaController {

    private final PizzaService service;
    private final PizzaRequestToPizza mapperRequestToEntity;
    private final PizzaToPizzaResponse mapperEntityToResponse;

    public PizzaController(PizzaService service, PizzaRequestToPizza mapperRequestToEntity, PizzaToPizzaResponse mapperEntityToResponse) {
        this.service = service;
        this.mapperRequestToEntity = mapperRequestToEntity;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas as pizzas",
            method = "GET",
            description = "Retorna uma lista com todas as pizzas."
    )
    public ResponseEntity<List<PizzaResponseDto>> getAllPizzas() {
        List<Pizza> pizzas = service.getAllPizzas();

        if (pizzas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<PizzaResponseDto> response = pizzas.stream().map(mapperEntityToResponse::map).toList();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma pizza por id", method = "GET")
    public ResponseEntity<PizzaResponseDto> getPizzaById(
            @PathVariable("id") Long id
    ) {
        Pizza pizza = service.getPizzaById(id);
        return ResponseEntity.status(200).body(mapperEntityToResponse.map(pizza));
    }

    @PostMapping
    @Operation(summary = "Salva uma nova pizza", method = "POST")
    public ResponseEntity<PizzaResponseDto> postPizza(
            @RequestBody @Valid PizzaRequestDto request
    ) {
        Pizza savedPizza = service.savePizza(mapperRequestToEntity.map(request));
        return ResponseEntity.status(201).body(mapperEntityToResponse.map(savedPizza));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pizza existente", method = "PUT")
    public ResponseEntity<PizzaResponseDto> putPizza(
            @PathVariable("id") Long id,
            @RequestBody @Valid PizzaRequestDto request
    ) {
        Pizza updatedPizza = service.updatePizza(id, mapperRequestToEntity.map(request));
        return ResponseEntity.status(200).body(mapperEntityToResponse.map(updatedPizza));
    }

}
