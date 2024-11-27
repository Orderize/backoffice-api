package com.orderize.backoffice_api.dto.order;

import java.math.BigDecimal;

public record OrderTotalPriceResponseDto(
        BigDecimal value
) {
}
