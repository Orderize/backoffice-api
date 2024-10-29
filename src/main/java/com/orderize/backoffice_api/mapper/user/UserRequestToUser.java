package com.orderize.backoffice_api.mapper.user;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.user.UserRequestDto;
import com.orderize.backoffice_api.model.Address;
import com.orderize.backoffice_api.model.Enterprise;
import com.orderize.backoffice_api.model.User;

@Component
public class UserRequestToUser {

    public User map(UserRequestDto userRequestDto, Address address, Enterprise enterprise) {
        return new User(
                enterprise,
                address,
                userRequestDto.phone(),
                userRequestDto.password(),
                userRequestDto.email(),
                userRequestDto.name(),
                null
        );
    }

}
