package com.orderize.backoffice_api.dto.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;

public record UserRequestDto(
        Long id,
        String name,
        @Email @Nullable String email,
        String password,
        String phone,
        Long address,
        Long enterprise
) {
    public UserRequestDto(String name, java.lang.String email, java.lang.String password, java.lang.String phone, Long address, Long enterprise) {
        this(null, name, email, password, phone, address, enterprise);
    }
}
