package com.orderize.backoffice_api.dto.flavor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FlavorRequestDto (
    @NotBlank String name,
    String description,
    @NotNull Double price,
    List<Long> ingredients
) {

    public FlavorRequestDto(String name, Double price) {
        this(name, null, price, null);
    }

    public FlavorRequestDto(String name, String description, Double price) {
        this(name, description, price, null);
    }
}
