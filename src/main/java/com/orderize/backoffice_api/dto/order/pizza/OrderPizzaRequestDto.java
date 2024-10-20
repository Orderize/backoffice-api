package com.orderize.backoffice_api.dto.order.pizza;

import jakarta.validation.constraints.NotNull;

public record OrderPizzaRequestDto (
        @NotNull
        Integer quantity,
        @NotNull
        Double grossPrice,
        @NotNull
        Double netPrice
) { }
