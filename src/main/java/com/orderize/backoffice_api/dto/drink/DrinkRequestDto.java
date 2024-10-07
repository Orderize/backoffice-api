package com.orderize.backoffice_api.dto.drink;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DrinkRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        BigDecimal price,
        @NotNull
        Integer milimeters
) {
}
