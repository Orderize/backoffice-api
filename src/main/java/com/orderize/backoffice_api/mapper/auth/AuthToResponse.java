package com.orderize.backoffice_api.mapper.auth;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.dto.user.UserInfoResponseDto;
import com.orderize.backoffice_api.mapper.enterprise.EnterpriseToEnterpriseResponse;
import com.orderize.backoffice_api.mapper.role.RoleToRoleResponse;
import com.orderize.backoffice_api.mapper.user.UserToUserResponseDto;
import com.orderize.backoffice_api.model.User;

@Component
public class AuthToResponse {

    private final UserToUserResponseDto userToUserResponseDto;
    private final EnterpriseToEnterpriseResponse enterpriseToEnterpriseResponse;
    private final RoleToRoleResponse roleToRoleResponse;

    public AuthToResponse(UserToUserResponseDto userToUserResponseDto, EnterpriseToEnterpriseResponse enterpriseToEnterpriseResponse, RoleToRoleResponse roleToRoleResponse) {
        this.userToUserResponseDto = userToUserResponseDto;
        this.enterpriseToEnterpriseResponse = enterpriseToEnterpriseResponse;
        this.roleToRoleResponse = roleToRoleResponse;
    }

    public LoginResponseDto map(String token) {

        return new LoginResponseDto(
            token
        );
    }

    public UserInfoResponseDto map(User user) {
        return new UserInfoResponseDto(
            user.getId(), 
            user.getName(),
            enterpriseToEnterpriseResponse.map(user.getEnterprise()),
            user.getRoles().stream().map(roleToRoleResponse::map).toList()
        );
    }

}
