package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.UserRoleRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/roles", produces = {"application/json"})
@Tag(name = "/users/roles")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Salva uma nova role em um usuário", method = "POST")
    public ResponseEntity<UserResponseDto> postUserRole(
            @RequestBody @Valid UserRoleRequestDto request
    ) {
        UserResponseDto userWithNewRole = service.saveNewRole(request);
        return ResponseEntity.status(214).body(userWithNewRole);
    }

    @DeleteMapping("/{userId}/{roleId}")
    @Operation(summary = "Deleta uma role de um usuário", method = "DELETE")
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable Long userId,
            @PathVariable Long roleId
    ) {
        service.deleteUserRole(userId, roleId);
        return ResponseEntity.status(204).build();
    }

}
