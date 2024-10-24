package com.orderize.backoffice_api.mapper.user;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.address.AddressResponseDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.User;

@Component
public class UserToUserResponseDto implements Mapper<User, UserResponseDto> {
    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                new AddressResponseDto(
                        user.getAddress().getId(),
                        user.getAddress().getCep(),
                        user.getAddress().getState(),
                        user.getAddress().getNumber(),
                        user.getAddress().getStreet(),
                        user.getAddress().getCity(),
                        user.getAddress().getNeighborhood()
                ),
                new EnterpriseResponseDto(
                        user.getEnterprise().getId(),
                        user.getEnterprise().getName(),
                        user.getEnterprise().getCnpj()
                ),
                user.getRoles() != null ?
                        user.getRoles().stream().map(it -> new RoleResponseDto(
                        it.getId(),
                        it.getName()
                )).toList()
        : Collections.emptyList()
        );
    }
}
