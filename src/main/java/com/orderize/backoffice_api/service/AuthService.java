package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.auth.AuthenticationDto;
import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.dto.user.UserInfoResponseDto;
import com.orderize.backoffice_api.mapper.auth.AuthToResponse;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.UserRepository;
import com.orderize.backoffice_api.security.TokenService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;
    private final AuthToResponse authToResponse;

    public AuthService(ApplicationContext applicationContext, UserRepository userRepository, TokenService tokenService, AuthToResponse authToResponse) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authToResponse = authToResponse;
    }

    public LoginResponseDto login( AuthenticationDto data){
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generationToken(user);
        
        return authToResponse.map(token);
    }

    public UserInfoResponseDto getUserInfo( String token ) {

        Long userId;
        try {
            token = token.replace("Bearer ", "");
            userId = tokenService.getUserIdFromToken(token);
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado.", e);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return authToResponse.map(user);
    }
}
