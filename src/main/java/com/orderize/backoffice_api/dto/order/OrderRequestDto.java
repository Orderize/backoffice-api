package com.orderize.backoffice_api.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.sql.Timestamp;

public record OrderRequestDto(
        @NotNull
        Long id,
        @NotNull
        Long client,
        @NotNull
        Long responsible,
        Timestamp datetime_order,
        @NotBlank
        String type,
        @NotNull
        Double freight,
        @NotNull
        Double estimativeTime,
        @NotNull
        Double grossPrice,
        @NotNull
        Double netPrice

) {
        public OrderRequestDto(Long client, Long responsible, Timestamp datetime_order, String type, Double freight, Double estimativeTime, Double grossPrice, Double netPrice){
                this(null, client, responsible, datetime_order, type, freight, estimativeTime, grossPrice, netPrice);
        }
}
