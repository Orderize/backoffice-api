package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.user.UserRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@Tag(name = "/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todos os usuários",
            method = "GET",
            description = "Pode receber os request param opcionais: [phone, email, idEnterprise, idRole]" +
                    " e filtra o resultado com base nos request param " +
                    "passados, caso nenhum seja passado retorna uma list com todos os usuários."
    )
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "idEnterprise", required = false) Long idEnterprise,
            @RequestParam(value = "idRole", required = false) Long idRole
    ) {
        List<UserResponseDto> users = service.getAllUsers(phone, email, idEnterprise, idRole);

        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(users);
        }
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
