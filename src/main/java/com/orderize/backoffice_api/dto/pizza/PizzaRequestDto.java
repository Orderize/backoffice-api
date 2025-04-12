package com.orderize.backoffice_api.dto.pizza;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PizzaRequestDto(
        @Size(max = 300) String observations,
        @NotNull List<Long> flavors,
        @NotBlank String border,
        @NotBlank String size,
        @NotBlank String mass
) {}
