package com.orderize.backoffice_api.dto.order;

import java.sql.Timestamp;

public record OrderResponseDto (
        Long id,
        Long client,
        Long responsible,
        Timestamp datetime_order,
        String type,
        Double freight,
        Double estimativeTime,
        Double grossPrice,
        Double netPrice
) {
}
