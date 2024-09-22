package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.model.Role;
import org.springframework.stereotype.Component;

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
