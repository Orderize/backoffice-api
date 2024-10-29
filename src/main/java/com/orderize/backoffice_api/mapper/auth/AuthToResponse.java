package com.orderize.backoffice_api.mapper.auth;

import org.springframework.stereotype.Component;

import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.mapper.user.UserToUserResponseDto;
import com.orderize.backoffice_api.model.User;

@Component
public class AuthToResponse {

    private final UserToUserResponseDto userToUserResponseDto;

    public AuthToResponse(UserToUserResponseDto userToUserResponseDto) {
        this.userToUserResponseDto = userToUserResponseDto;
    }

    public LoginResponseDto map(String token, User user) {

        return new LoginResponseDto(
                token,
                userToUserResponseDto.map(user)
        );
    }

}
