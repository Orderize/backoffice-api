package com.orderize.backoffice_api.mapper.role;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Role;

@Component
public class RoleToRoleResponse implements Mapper<Role, RoleResponseDto>{

    @Override
    public RoleResponseDto map(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getName()
        );
    }

}
