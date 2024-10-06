package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.model.Role;
import org.springframework.stereotype.Component;

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
