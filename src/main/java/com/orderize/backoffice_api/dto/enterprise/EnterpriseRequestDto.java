package com.orderize.backoffice_api.dto.enterprise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnterpriseRequestDto(
        @NotBlank String name,
        @Size(max = 18) String cnpj
) {
}
