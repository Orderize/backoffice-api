package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.auth.AuthenticationDto;
import com.orderize.backoffice_api.dto.auth.LoginResponseDto;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.UserRepository;
import com.orderize.backoffice_api.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final ApplicationContext applicationContext;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AuthService(ApplicationContext applicationContext, UserRepository userRepository, TokenService tokenService) {
        this.applicationContext = applicationContext;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponseDto login( AuthenticationDto data){
        authenticationManager = applicationContext.getBean(AuthenticationManager.class);
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generationToken((User) auth.getPrincipal());
        return new LoginResponseDto(token);
    }
}