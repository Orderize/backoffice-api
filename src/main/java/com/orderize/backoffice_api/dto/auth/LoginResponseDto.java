package com.orderize.backoffice_api.dto.auth;

import com.orderize.backoffice_api.dto.user.UserResponseDto;

public record LoginResponseDto(
    String token,
    UserResponseDto user
) {}
