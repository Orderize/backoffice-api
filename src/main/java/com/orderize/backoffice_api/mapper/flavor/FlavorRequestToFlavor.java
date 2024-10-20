package com.orderize.backoffice_api.mapper.flavor;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.flavor.FlavorRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Flavor;

@Component
public class FlavorRequestToFlavor implements Mapper<FlavorRequestDto, Flavor> {
    @Override
    public Flavor map(FlavorRequestDto flavorRequestDto) {
        return new Flavor(
                flavorRequestDto.name(),
                flavorRequestDto.description(),
                flavorRequestDto.price()
        );
    }

    public Flavor map(Flavor flavor, FlavorRequestDto flavorRequestDto) {
        return new Flavor(
            flavor.getId(),
            flavorRequestDto.name(),
            flavorRequestDto.description() != null ? flavorRequestDto.description() : flavor.getDescription(),
            flavorRequestDto.price(),
            flavor.getRegistered(),
            flavor.getIngredients(),
            flavor.getPizzas()
        );
    }
}
