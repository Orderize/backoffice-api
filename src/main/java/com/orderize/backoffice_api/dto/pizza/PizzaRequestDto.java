package com.orderize.backoffice_api.dto.pizza;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record PizzaRequestDto(
        @NotBlank String name,
        @NotNull BigDecimal price,
        @Size(max = 300) String observations,
        @NotNull Long flavorId
) {
    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.observations;
    }
}
