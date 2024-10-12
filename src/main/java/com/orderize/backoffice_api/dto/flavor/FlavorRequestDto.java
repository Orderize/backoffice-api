package com.orderize.backoffice_api.dto.flavor;

import jakarta.validation.constraints.NotBlank;

public record FlavorRequestDto (
    @NotBlank String name,
    String description
) {
}
