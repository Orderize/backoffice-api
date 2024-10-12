package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.model.Pizza;
import org.springframework.stereotype.Component;

@Component
public class PizzaRequestToPizza implements Mapper<PizzaRequestDto, Pizza> {
    @Override
    public Pizza map(PizzaRequestDto pizzaRequestDto) {
        return new Pizza(
                null,
                pizzaRequestDto.name(),
                pizzaRequestDto.price(),
                pizzaRequestDto.observations(),
                pizzaRequestDto.flavorId()
        );
    }
}
