package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseRequestDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.service.EnterpriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enterprises", produces = "application/json")
@Tag(name = "/enterprises")
public class EnterpriseController {

    private final EnterpriseService service;

    public EnterpriseController(EnterpriseService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas as empresas",
            method = "GET",
            description = "Pode receber os request param opcionais: [name]" +
                    " e filtra o resultado com base nos request param " +
                    "passados, caso nenhum seja passado retorna uma list com todos as empresas."
    )
    public ResponseEntity<List<EnterpriseResponseDto>> getAllEnterprises(
            @RequestParam(value = "name", required = false) String name
    ) {
        List<EnterpriseResponseDto> enterprises = service.getAllEnterprises(name);

        if (enterprises.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(enterprises);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma empresa por id", method = "GET")
    public ResponseEntity<EnterpriseResponseDto> getEnterpriseById(
            @PathVariable("id") Long id
    ) {
        EnterpriseResponseDto enterprise = service.getEnterpriseById(id);
        return ResponseEntity.status(200).body(enterprise);
    }

    @PostMapping
    @Operation(summary = "Salva uma nova empresa", method = "POST")
    public ResponseEntity<EnterpriseResponseDto> postEnterprise(
            @RequestBody @Valid EnterpriseRequestDto request
    ) {
        EnterpriseResponseDto savedEnterprise = service.saveEnterprise(request);
        return ResponseEntity.status(201).body(savedEnterprise);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma empresa existente", method = "PUT")
    public ResponseEntity<EnterpriseResponseDto> putEnterprise(
            @PathVariable("id") Long id,
            @RequestBody @Valid EnterpriseRequestDto request
    ) {
        EnterpriseResponseDto updatedEnterprise = service.updateEnterprise(id, request);
        return ResponseEntity.status(200).body(updatedEnterprise);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma empresa existente", method = "DELETE")
    public ResponseEntity<Void> deleteEnterprise(
            @PathVariable("id") Long id
    ) {
        service.deleteEnterprise(id);
        return ResponseEntity.status(204).build();
    }

}
