package com.orderize.backoffice_api.dto.flavor;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FlavorRequestDto (
    @NotBlank String name,
    String description,
    @NotNull BigDecimal price,
    Long ingredient
) {

    public FlavorRequestDto(String name, BigDecimal price) {
        this(name, null, price, null);
    }

    public FlavorRequestDto(String name, String description, BigDecimal price) {
        this(name, description, price, null);
    }
}
