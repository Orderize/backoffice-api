package com.orderize.backoffice_api.dto.order;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(
        @NotNull Long client,
        @NotNull Long responsible,
        List<Long> pizzas,
        List<Long> drinks,
        String type,
        @NotNull BigDecimal freight,
        @NotNull Integer estimatedTime,
        @NotNull BigDecimal price

) {
}
