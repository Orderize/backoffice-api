package com.orderize.backoffice_api.dto.promotion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PromotionRequestDto(
        @NotBlank
        String name,
        @NotNull
        String description,
        @NotNull
        BigDecimal discountValue,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate,
        @NotNull
        List<PromotionConditionRequestDto> conditions
) { }
