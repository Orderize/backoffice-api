package com.orderize.backoffice_api.dto.password_reset;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    @NotBlank(message = "Token é obrigatório.")
    private String token;
}
