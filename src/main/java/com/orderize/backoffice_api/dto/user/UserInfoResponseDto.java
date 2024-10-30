package com.orderize.backoffice_api.dto.user;

import java.util.List;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;

public record UserInfoResponseDto(
    Long id,
    String name,
    EnterpriseResponseDto enterprise,
    List<RoleResponseDto> roles
) {
}
