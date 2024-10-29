package com.orderize.backoffice_api.dto.attestation;

import com.orderize.backoffice_api.dto.role.RoleResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// TODO: QUANDO ORDER TIVER A FORMA DE PAGAMENTO INCLUIR NESTE DTO
public record AttestationResponseDto(
        Long id,
        Long orderId,
        LocalDate createdTime,
        BigDecimal totalValue,
        String orderType,
        AttestationClientDto client,
        AttestationOrderCreatorDto orderCreator,
        AttestationCompanyDto company,
        List<AttestationOrderItemDto> orderItens
) {

    public record AttestationClientDto(
            Long id,
            String name,
            String phone,
            String street,
            Integer houseNumber,
            String neighborhood
    ) {}

    public record AttestationOrderCreatorDto(
            Long id,
            String name,
            String email,
            List<RoleResponseDto> roles
    ) {}

    public record AttestationCompanyDto(
            Long id,
            String name,
            String cnpj
    ) {}

    public record AttestationOrderItemDto(
            String name,
            BigDecimal price
    ) {}

}
