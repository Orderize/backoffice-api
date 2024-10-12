package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
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

    public PizzaController(PizzaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas as pizzas",
            method = "GET",
            description = "Pode receber os request param opcionais: [name]" +
                    " e filtra o resultado com base nos request param " +
                    "passados, caso nenhum seja passado retorna uma lista com todas as pizzas."
    )
    public ResponseEntity<List<PizzaResponseDto>> getAllPizzas(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<PizzaResponseDto> pizzas = service.getAllPizzas(name);

        if (pizzas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(pizzas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma pizza por id", method = "GET")
    public ResponseEntity<PizzaResponseDto> getPizzaById(
            @PathVariable("id") Long id
    ) {
        PizzaResponseDto pizza = service.getPizzaById(id);
        return ResponseEntity.status(200).body(pizza);
    }

    @PostMapping
    @Operation(summary = "Salva uma nova pizza", method = "POST")
    public ResponseEntity<PizzaResponseDto> postPizza(
            @RequestBody @Valid PizzaRequestDto request
    ) {
        PizzaResponseDto savedPizza = service.savePizza(request);
        return ResponseEntity.status(201).body(savedPizza);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma pizza existente", method = "PUT")
    public ResponseEntity<PizzaResponseDto> putPizza(
            @PathVariable("id") Long id,
            @RequestBody @Valid PizzaRequestDto request
    ) {
        PizzaResponseDto updatedPizza = service.updatePizza(id, request);
        return ResponseEntity.status(200).body(updatedPizza);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma pizza existente", method = "DELETE")
    public ResponseEntity<Void> deletePizza(
            @PathVariable("id") Long id
    ) {
        service.deletePizza(id);
        return ResponseEntity.status(204).build();
    }
}
