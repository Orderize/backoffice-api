package com.orderize.backoffice_api.mapper.flavor;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Flavor;

@Component
public class FlavorToFlavorResponse implements Mapper<Flavor, FlavorResponseDto> {
    @Override
    public FlavorResponseDto map(Flavor flavor) {
        return new FlavorResponseDto(
                flavor.getId(),
                flavor.getName(),
                flavor.getDescription(),
                flavor.getRegistered()
        );
    }
}
