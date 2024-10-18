package com.orderize.backoffice_api.dto.promotion;

import java.math.BigDecimal;

public record PromotionConditionResponseDto(
        Long promotionId,
        Long conditionId,
        String conditionTypeName,
        BigDecimal requiredValue,
        String idsRequiredItens
) {
}
