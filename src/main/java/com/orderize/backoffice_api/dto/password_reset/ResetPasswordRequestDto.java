package com.orderize.backoffice_api.dto.password_reset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido")
    private String email;
}
