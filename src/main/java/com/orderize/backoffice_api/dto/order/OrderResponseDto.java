package com.orderize.backoffice_api.dto.order;

public record OrderResponseDto (
        Long id,
        Long client,
        Long responsible,
        String type,
        Double freight,
        Double estimativeTime
) { }
