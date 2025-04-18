package com.orderize.backoffice_api.controller;

import java.util.List;

import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orderize.backoffice_api.dto.flavor.FlavorRequestDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.service.FlavorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/flavors", produces = "application/json")
@Tag(name = "/flavors")
public class FlavorController {

    private final FlavorService service;
    private final FlavorToFlavorResponseDto mapperEntityToResponse;

    public FlavorController(FlavorService service, FlavorToFlavorResponseDto mapperEntityToResponse) {
        this.service = service;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    @GetMapping
    @Operation(
            summary = "Busca todos os sabores",
            method = "GET",
            description = "Retorna uma lista com todos os sabores."
    )
    public ResponseEntity<List<FlavorResponseDto>> getAllFlavors() {
        List<Flavor> flavors = service.getAllFlavors();

        if (flavors.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(flavors.stream().map(it ->
                mapperEntityToResponse.map(it)).toList());
    }

    @GetMapping("/pop")
    @Operation(summary = "Busca todos os sabores por popularidade", method = "GET")
    public ResponseEntity<List<FlavorResponseDto>> getAllFlavorsByPop(@RequestParam String value) {
        List<Flavor> flavors = service.getAllFlavorsByPop(value);
        
        if (flavors.isEmpty()) {
            return ResponseEntity.status(204).build();
        } 
        
        return ResponseEntity.status(200).body(flavors.stream().map(it ->
                mapperEntityToResponse.map(it)).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um sabor por id", method = "GET")
    public ResponseEntity<FlavorResponseDto> getFlavorById(
            @PathVariable("id") Long id
    ) {
        Flavor flavor = service.getFlavorById(id);
        return ResponseEntity.status(200).body(mapperEntityToResponse.map(flavor));
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
