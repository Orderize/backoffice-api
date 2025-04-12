package com.orderize.backoffice_api.mapper.pizza;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.service.PizzaService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PizzaRequestToPizza implements Mapper<PizzaRequestDto, Pizza> {

    private final PizzaService pizzaService;

    public PizzaRequestToPizza(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @Override
    public Pizza map(PizzaRequestDto pizzaRequestDto) {
        List<Flavor> flavors = pizzaService.getPizzaFlavors(pizzaRequestDto.flavors());
        BigDecimal price = pizzaService.getPrice(flavors);
        String name = pizzaService.getPizzaName(flavors);

        return new Pizza(
                null,
                name,
                price,
                pizzaRequestDto.observations(),
                flavors,
                pizzaRequestDto.border(),
                pizzaRequestDto.size(),
                pizzaRequestDto.mass()
        );
    }

}
