package com.orderize.backoffice_api.mapper.pizza;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;

@Component
public class PizzaToPizzaResponseDto {

    private final FlavorToFlavorResponseDto flavorToFlavorResponseDto;

    public PizzaToPizzaResponseDto(FlavorToFlavorResponseDto flavorToFlavorResponseDto) {
        this.flavorToFlavorResponseDto = flavorToFlavorResponseDto;
    }


    // Incluir ingredients aqui nesse método
    public PizzaResponseDto map(Pizza pizza) {
        return new PizzaResponseDto(
                pizza.getIdPizza(),
                pizza.getName(),
                pizza.getPrice(),
                pizza.getObservations(),
                pizza.getFlavors().stream().map(flavor -> flavorToFlavorResponseDto.map(flavor)).collect(Collectors.toList())
        );
    }
}
