package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.pizza.FlavorResponseDto; // Importar FlavorResponseDto
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.model.Flavor; // Importar Flavor
import com.orderize.backoffice_api.model.Pizza;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
