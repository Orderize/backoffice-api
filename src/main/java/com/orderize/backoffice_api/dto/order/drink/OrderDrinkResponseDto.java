package com.orderize.backoffice_api.dto.order.drink;

public record OrderDrinkResponseDto (
        Long orderId,
        Long drinkId,
        Integer quantity,
        Double grossPrice,
        Double netPrice
) { }
