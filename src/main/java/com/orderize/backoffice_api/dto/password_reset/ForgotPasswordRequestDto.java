package com.orderize.backoffice_api.dto.password_reset;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequestDto {
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Email inválido")
        private String email;
}
