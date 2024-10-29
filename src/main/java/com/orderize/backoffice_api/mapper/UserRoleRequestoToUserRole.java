package com.orderize.backoffice_api.mapper;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.UserRoleRequestDto;
import com.orderize.backoffice_api.model.UserRole;

@Component
public class UserRoleRequestoToUserRole implements Mapper<UserRoleRequestDto, UserRole> {

    @Override
    public UserRole map(UserRoleRequestDto userRoleRequestDto) {
        return new UserRole(
                userRoleRequestDto.userId(),
                userRoleRequestDto.roleId()
        );
    }

}
