package com.orderize.backoffice_api.dto.order;

import java.sql.Timestamp;
import java.util.List;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.model.User;

public record OrderResponseDto (
        Long id,
        User client,
        User responsible,
        List<PizzaResponseDto> pizzas,
        List<DrinkResponseDto> drinks,
        Timestamp datetime_order,
        String type,
        Double freight,
        Double estimativeTime,
        Double grossPrice,
        Double netPrice
) {
}
