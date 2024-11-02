package com.orderize.backoffice_api.mapper.attestation;

import com.orderize.backoffice_api.dto.attestation.AttestationResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.mapper.role.RoleToRoleResponse;
import com.orderize.backoffice_api.model.Attestation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttestationToAttestationResponse implements Mapper<Attestation, AttestationResponseDto> {

    private final RoleToRoleResponse mapperRoleToRoleResponse;

    public AttestationToAttestationResponse(RoleToRoleResponse mapperRoleToRoleResponse) {
        this.mapperRoleToRoleResponse = mapperRoleToRoleResponse;
    }

    @Override
    public AttestationResponseDto map(Attestation attestation) {
        List<AttestationResponseDto.AttestationOrderItemDto> itens = new ArrayList();

        itens.addAll(
                attestation.getOrder().getPizzas().stream().map(
                        it -> new AttestationResponseDto.AttestationOrderItemDto(
                                it.getName(),
                                it.getPrice()
                        )
                ).toList()
        );

        itens.addAll(
                attestation.getOrder().getDrinks().stream().map(
                        it -> new AttestationResponseDto.AttestationOrderItemDto(
                                it.getName(),
                                it.getPrice()
                        )
                ).toList()
        );

        return new AttestationResponseDto(
                attestation.getId(),
                attestation.getOrder().getId(),
                attestation.getCreatedTime(),
                attestation.getOrder().getPrice(),
                attestation.getOrder().getType(),
                new AttestationResponseDto.AttestationClientDto(
                        attestation.getOrder()
                                .getClient().getId(),
                        attestation.getOrder()
                                .getClient().getName(),
                        attestation.getOrder()
                                .getClient().getPhone(),
                        attestation.getOrder()
                                .getClient().getAddress()
                                .getStreet(),
                        attestation.getOrder()
                                .getClient().getAddress()
                                .getNumber(),
                        attestation.getOrder()
                                .getClient().getAddress()
                                .getNeighborhood()
                ),
                new AttestationResponseDto.AttestationOrderCreatorDto(
                        attestation.getOrder().getResponsible()
                                .getId(),
                        attestation.getOrder().getResponsible()
                                .getName(),
                        attestation.getOrder().getResponsible()
                                .getEmail(),
                        attestation.getOrder().getResponsible()
                                .getRoles().stream().map(
                                        it -> mapperRoleToRoleResponse.map(it)
                                ).toList()
                ),
                new AttestationResponseDto.AttestationCompanyDto(
                        attestation.getOrder().getResponsible()
                                .getEnterprise().getId(),
                        attestation.getOrder().getResponsible()
                                .getEnterprise().getName(),
                        attestation.getOrder().getResponsible()
                                .getEnterprise().getCnpj()
                ),
                itens
        );
    }

}
