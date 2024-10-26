package com.orderize.backoffice_api.dto.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;

public record OrderResponseDto (
        Long id,
        UserResponseDto client,
        UserResponseDto responsible,
        List<PizzaResponseDto> pizzas,
        List<DrinkResponseDto> drinks,
        Timestamp datetime,
        String type,
        BigDecimal freight,
        Integer estimatedTime,
        BigDecimal price
) {
}
