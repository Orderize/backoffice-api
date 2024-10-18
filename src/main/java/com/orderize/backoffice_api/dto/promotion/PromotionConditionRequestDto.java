package com.orderize.backoffice_api.dto.promotion;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PromotionConditionRequestDto(
        @NotNull
        Long conditionType,
        BigDecimal requiredValue,
        String idsRequiredItens
) {
}
