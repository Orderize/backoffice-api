package com.orderize.backoffice_api.mapper.role;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Role;

@Component
public class RoleRequestToRole implements Mapper<RoleRequestDto, Role> {

    @Override
    public Role map(RoleRequestDto roleRequestDto) {
        return new Role(
                null,
                roleRequestDto.name()
        );
    }

}
