package com.orderize.backoffice_api.dto.enterprise;

public record EnterpriseResponseDto(
        Long id,
        String name,
        String cnpj
) {
}
