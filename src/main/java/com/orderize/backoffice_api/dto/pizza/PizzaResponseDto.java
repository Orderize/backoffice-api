package com.orderize.backoffice_api.dto.pizza;

import java.math.BigDecimal;
import java.util.List;

import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;

public record PizzaResponseDto (    
    Long id,
    String name,
    BigDecimal price,
    String observations,
    List<FlavorResponseDto> flavors,
    String border,
    String size,
    String mass
){}
