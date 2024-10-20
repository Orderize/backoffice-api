package com.orderize.backoffice_api.dto.viaCep;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ViaCepRequestDto(
        @NotBlank
        String cep,
        @NotNull
        Integer number
) {
}
