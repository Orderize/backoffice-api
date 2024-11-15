package com.orderize.backoffice_api.mapper.pizza;

import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.Pizza;
import org.springframework.stereotype.Component;

@Component
public class PizzaToPizzaResponse implements Mapper<Pizza, PizzaResponseDto> {

    private final FlavorToFlavorResponseDto flavorMapper;

    public PizzaToPizzaResponse(FlavorToFlavorResponseDto flavorMapper) {
        this.flavorMapper = flavorMapper;
    }

    @Override
    public PizzaResponseDto map(Pizza pizza) {
        return new PizzaResponseDto(
                pizza.getId(),
                pizza.getName(),
                pizza.getPrice(),
                pizza.getObservations(),
                pizza.getFlavors().stream().map(flavorMapper::map).toList()
        );
    }

}
