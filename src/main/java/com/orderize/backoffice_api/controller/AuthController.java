package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.auth.AuthenticationDto;
import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.dto.password_reset.ResetPasswordRequestDto;
import com.orderize.backoffice_api.dto.user.UserInfoResponseDto;
import com.orderize.backoffice_api.service.AuthService;
import com.orderize.backoffice_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
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

    @PostMapping("/reset-password")
    @Operation(summary = "Inicia o processo de redefinição de senha", method = "POST",
            description = "Envia a senha do usuário gerada automaticamente e enviada por e-mail, se ele estiver cadastrado.")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDto request) {
        try {
            userService.resetPassword(request.getEmail());
            return ResponseEntity.ok("Se o e-mail estiver cadastrado, sua senha foi redefinida com sucesso e enviada para o seu e-mail. ");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
