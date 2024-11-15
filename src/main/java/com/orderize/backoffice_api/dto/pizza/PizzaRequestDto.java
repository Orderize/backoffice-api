package com.orderize.backoffice_api.dto.pizza;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PizzaRequestDto(
        @NotEmpty List<Long> flavor,
        @Size(max = 300) String observations
) {
}
