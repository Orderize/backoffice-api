package com.orderize.backoffice_api.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDto(
        Long id,
        @NotBlank String cep,
        @NotBlank String state,
        @NotBlank Integer number,
        String street,
        String city,
        String neighborhood
) {

    public AddressRequestDto(String state, String cep, Integer number) {
        this(null, cep, state, number, null, null, null);
    }

}
