package com.orderize.backoffice_api.dto.order.drink;

import jakarta.validation.constraints.NotNull;

public record OrderDrinkRequestDto (
        @NotNull
        Integer quantity,
        @NotNull
        Double grossPrice,
        @NotNull
        Double netPrice
) { }
