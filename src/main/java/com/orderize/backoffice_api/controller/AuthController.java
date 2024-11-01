package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.auth.AuthenticationDto;
import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.dto.user.UserInfoResponseDto;
import com.orderize.backoffice_api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth", produces = {"application/json"})
@Tag(name = "/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Faz login", method = "POST", description = "Caso o login seja realizado com sucesso, retor" +
            "na um token JWT que deve ser utilizado como header Authorization para fazer requisições")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody AuthenticationDto authDto
    ) {
        return ResponseEntity.status(200).body(authService.login(authDto));
    }

    @GetMapping("/user/info")
    @Operation(summary = "Pega informações do usuário", method = "GET", description = "Retorna um objeto de informações de usuário limitado")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.status(200).body(authService.getUserInfo(authorization));
    } 
}
