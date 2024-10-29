package com.orderize.backoffice_api.dto.attestation;

import jakarta.validation.constraints.NotNull;

public record AttestationRequestDto(
        @NotNull
        Long order
) {
}
