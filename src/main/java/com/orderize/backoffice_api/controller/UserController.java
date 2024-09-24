package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.user.UserRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por id", method = "GET")
    public ResponseEntity<UserResponseDto> getUserById(
            @PathVariable("id") Long id
    ) {
        UserResponseDto user = service.getUserById(id);

        if (user != null) {
            return ResponseEntity.status(200).body(user);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @PostMapping
    @Operation(summary = "Salva um novo usuário", method = "POST")
    public ResponseEntity<UserResponseDto> saveUser(
            @RequestBody @Valid UserRequestDto userRequest
    ) {
        UserResponseDto user = service.saveUser(userRequest);
        return ResponseEntity.status(201).body(user);
    }

    @PutMapping
    @Operation(summary = "Atualiza um usuário", method = "PUT")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestBody UserRequestDto userToUpdate
    ) {
        UserResponseDto user = service.updateUser(userToUpdate);
        if (user != null) {
            return ResponseEntity.status(201).body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um endereço", method = "DELETE")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("id") Long id
    ) {
        Boolean canBeDeleted = service.deleteUser(id);

        if (canBeDeleted) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
