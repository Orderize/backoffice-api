package com.orderize.backoffice_api.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDto(
        @NotBlank @Size(min = 4, max = 20) String name
) { }
