package com.orderize.backoffice_api.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public record OrderRequestDto(
        // acho que na requisição não necessariamente vai ter o id para inserir, caso queira atualizar o id vai ser mandado na uri
        // @NotNull
        // Long id,
        @NotNull Long client,
        @NotNull Long responsible,
        @NotNull List<Long> pizzas,
        @NotNull List<Long> drinks,
        Timestamp datetime_order,
        @NotBlank String type,
        @NotNull BigDecimal freight,
        @NotNull Double estimativeTime,
        @NotNull BigDecimal price

) {
}
