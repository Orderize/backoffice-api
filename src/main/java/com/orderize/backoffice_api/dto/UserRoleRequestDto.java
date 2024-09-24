package com.orderize.backoffice_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserRoleRequestDto(
        @NotNull
        @Positive
        Long userId,
        @NotNull
        @Positive
        Long roleId
) {
}
