package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.model.Role;
import com.orderize.backoffice_api.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> postRole(
            @RequestBody @Valid RoleRequestDto roleRequest
    ) {
        RoleResponseDto role = service.saveRole(roleRequest);
        return ResponseEntity.status(201).body(role);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        List<RoleResponseDto> roles = service.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(roles);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> putRole(
            @PathVariable Long id,
            @RequestBody @Valid RoleRequestDto role
    ) {
        RoleResponseDto updatedRole = service.updateRole(id, role);
        return ResponseEntity.status(200).body(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long id
    ) {
        service.deleteRole(id);
        return ResponseEntity.status(204).build();
    }
}
