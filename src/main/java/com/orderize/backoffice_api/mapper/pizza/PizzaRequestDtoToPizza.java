package com.orderize.backoffice_api.mapper.pizza;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;

@Component
public class PizzaRequestDtoToPizza {

    public Pizza map(PizzaRequestDto pizzaRequestDto, Flavor flavor) {
        return new Pizza(
                pizzaRequestDto.name(),
                pizzaRequestDto.price(),
                pizzaRequestDto.observations(),
                List.of(flavor)
        );
    }

    public Pizza map(PizzaRequestDto pizzaRequestDto, Pizza pizza, Flavor flavor) {
        List<Flavor> flavors = new ArrayList<>(pizza.getFlavor());
        flavors.add(flavor);

        return new Pizza(
            pizza.getIdPizza(),
            pizzaRequestDto.name(),
            pizzaRequestDto.price(),
            pizzaRequestDto.observations(),
            flavors        
        );
    }
}
