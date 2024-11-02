package com.orderize.backoffice_api.dto.user;

import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;

import java.util.List;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        String phone,
        AddressResponseDto address,
        EnterpriseResponseDto enterprise,
        List<RoleResponseDto> roles
) {
}
