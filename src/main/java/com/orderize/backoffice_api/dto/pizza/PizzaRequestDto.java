package com.orderize.backoffice_api.dto.pizza;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PizzaRequestDto(
        @NotBlank String name,
        @NotNull BigDecimal price,
        @Size(max = 300) String observations,
        @NotNull List<Long> flavors,
        @NotBlank String border,
        @NotBlank String size,
        @NotBlank String mass
) {}
