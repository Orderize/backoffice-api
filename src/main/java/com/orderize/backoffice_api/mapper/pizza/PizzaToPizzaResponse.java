package com.orderize.backoffice_api.mapper.pizza;

import java.util.List; // Importar FlavorResponseDto
import java.util.stream.Collectors;

import org.springframework.stereotype.Component; // Importar Flavor

import com.orderize.backoffice_api.dto.pizza.FlavorResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;

@Component
public class PizzaToPizzaResponse {

    public PizzaResponseDto map(Pizza pizza, List<Flavor> flavors) {
        return new PizzaResponseDto(
                pizza.getIdPizza(),
                pizza.getName(),
                pizza.getPrice(),
                pizza.getObservations(),
                flavors.stream().map(this::mapFlavorToDto).collect(Collectors.toList())
        );
    }

    private FlavorResponseDto mapFlavorToDto(Flavor flavor) {
        return new FlavorResponseDto(flavor.getId(), flavor.getName());
    }
}
