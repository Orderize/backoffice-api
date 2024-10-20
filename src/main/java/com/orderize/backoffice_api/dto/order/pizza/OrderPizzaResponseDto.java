package com.orderize.backoffice_api.dto.order.pizza;

public record OrderPizzaResponseDto (
        Long orderId,
        Long pizzaId,
        Integer quantity,
        Double grossPrice,
        Double netPrice
) { }
