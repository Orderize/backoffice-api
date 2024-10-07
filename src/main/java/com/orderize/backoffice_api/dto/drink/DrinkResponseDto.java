package com.orderize.backoffice_api.dto.drink;

import java.math.BigDecimal;

public record DrinkResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer milimeters
) {}
