package com.orderize.backoffice_api.dto.flavor;

public record FlavorResponseDto (
    Long id,
    String name,
    String description,
    String registered
) {

}
