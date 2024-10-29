package com.orderize.backoffice_api.dto.viaCep;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepApiResponse(
        @JsonProperty("cep")
        String cep,
        @JsonProperty("estado")
        String state,
        @JsonProperty("logradouro")
        String street,
        @JsonProperty("localidade")
        String city,
        @JsonProperty("bairro")
        String neighborhood
) { }
