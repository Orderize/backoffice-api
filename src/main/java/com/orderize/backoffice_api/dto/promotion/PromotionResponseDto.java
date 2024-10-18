package com.orderize.backoffice_api.dto.promotion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PromotionResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal discountValue,
        LocalDate startDate,
        LocalDate endDate,
        List<PromotionConditionResponseDto> conditions
) {
}
