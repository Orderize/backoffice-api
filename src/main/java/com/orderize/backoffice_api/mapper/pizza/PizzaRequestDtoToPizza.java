package com.orderize.backoffice_api.mapper.pizza;

import java.util.List;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;

@Component
public class PizzaRequestDtoToPizza {

    public Pizza map(PizzaRequestDto pizzaRequestDto) {
        return new Pizza(
                pizzaRequestDto.name(),
                pizzaRequestDto.price(),
                pizzaRequestDto.observations(),
                pizzaRequestDto.border(),
                pizzaRequestDto.size(),
                pizzaRequestDto.mass()
        );
    }
}
