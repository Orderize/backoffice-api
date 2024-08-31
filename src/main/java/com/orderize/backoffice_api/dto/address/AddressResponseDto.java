package com.orderize.backoffice_api.dto.address;

public record AddressResponseDto(
        Long id,
        String cep,
        String state,
        Integer number,
        String street,
        String city,
        String neighborhood
) { }
