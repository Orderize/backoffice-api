package com.orderize.backoffice_api.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDto(
        @NotNull
        Long id,
        @NotNull
        Long client,
        @NotNull
        Long responsible,
        @NotBlank
        String type,
        @NotNull
        Double freight,
        @NotNull
        Double estimativeTime
) {
        public OrderRequestDto(Long client, Long responsible, String type, Double freight, Double estimativeTime){
                this(null, client, responsible, type, freight, estimativeTime);
        }
}
