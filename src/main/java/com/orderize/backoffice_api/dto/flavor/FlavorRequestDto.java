package com.orderize.backoffice_api.dto.flavor;

import jakarta.validation.constraints.NotBlank;

public record FlavorRequestDto (
    @NotBlank String name,
    String description,
    Long ingredient
) {

    public FlavorRequestDto(String name) {
        this(name, null, null);
    }

    public FlavorRequestDto(String name, String description) {
        this(name, description, null);
    }
}
