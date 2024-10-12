package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.service.FlavorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.orderize.backoffice_api.dto.flavor.FlavorRequestDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;

@RestController
@RequestMapping(value = "/flavors", produces = "application/json")
@Tag(name = "/flavors")
public class FlavorController {

    private final FlavorService service;

    public FlavorController(FlavorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas os sabores",
            method = "GET",
            description = "Retorna uma lista com todos os sabores."
    )
    public ResponseEntity<List<FlavorResponseDto>> getAllFlavors() {
        List<FlavorResponseDto> flavors = service.getAllFlavors();

        if (flavors.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(flavors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um sabor por id", method = "GET")
    public ResponseEntity<FlavorResponseDto> getFlavorById(
            @PathVariable("id") Long id
    ) {
        FlavorResponseDto enterprise = service.getFlavorById(id);
        return ResponseEntity.status(200).body(enterprise);
    }

    @PostMapping
    @Operation(summary = "Salva um novo sabor", method = "POST")
    public ResponseEntity<FlavorResponseDto> postFlavor(
            @RequestBody @Valid FlavorRequestDto request
    ) {
        FlavorResponseDto savedFlavor = service.saveFlavor(request);
        return ResponseEntity.status(201).body(savedFlavor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um sabor existente", method = "PUT")
    public ResponseEntity<FlavorResponseDto> putFlavor(
            @PathVariable("id") Long id,
            @RequestBody @Valid FlavorRequestDto request
    ) {
        FlavorResponseDto updatedFlavor = service.updateFlavor(id, request);
        return ResponseEntity.status(200).body(updatedFlavor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um sabor existente", method = "DELETE")
    public ResponseEntity<Void> deleteFlavor(
            @PathVariable("id") Long id
    ) {
        service.deleteFlavor(id);
        return ResponseEntity.status(204).build();
    }

}
