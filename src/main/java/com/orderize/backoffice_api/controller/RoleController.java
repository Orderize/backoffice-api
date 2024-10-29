package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "roles", produces = {"application/json"})
@Tag(name = "/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Salva uma nova role", method = "POST")
    public ResponseEntity<RoleResponseDto> postRole(
            @RequestBody @Valid RoleRequestDto roleRequest
    ) {
        RoleResponseDto role = service.saveRole(roleRequest);
        return ResponseEntity.status(201).body(role);
    }

    @GetMapping
    @Operation(summary = "Lista todas as roles", method = "GET")
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> roles = service.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(roles);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma role", method = "PUT")
    public ResponseEntity<RoleResponseDto> putRole(
            @PathVariable Long id,
            @RequestBody @Valid RoleRequestDto role
    ) {
        RoleResponseDto updatedRole = service.updateRole(id, role);
        return ResponseEntity.status(200).body(updatedRole);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma role", method = "DELETE")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long id
    ) {
        service.deleteRole(id);
        return ResponseEntity.status(204).build();
    }
}
