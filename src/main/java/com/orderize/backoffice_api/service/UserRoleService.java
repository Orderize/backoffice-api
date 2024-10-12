package com.orderize.backoffice_api.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.UserRoleRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.UserRoleRequestoToUserRole;
import com.orderize.backoffice_api.mapper.user.UserToUserResponseDto;
import com.orderize.backoffice_api.model.UserRole;
import com.orderize.backoffice_api.repository.RoleRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import com.orderize.backoffice_api.repository.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserToUserResponseDto mapperUserToUserResponse;
    private final UserRoleRequestoToUserRole mapperUserRoleRequestToUserRole;

    public UserRoleService(UserRoleRepository repository, UserRepository userRepository, RoleRepository roleRepository, UserToUserResponseDto mapperUserToUserResponse, UserRoleRequestoToUserRole mapperUserRoleRequestToUserRole) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mapperUserToUserResponse = mapperUserToUserResponse;
        this.mapperUserRoleRequestToUserRole = mapperUserRoleRequestToUserRole;
    }

    public UserResponseDto saveNewRole(UserRoleRequestDto request) {
        if (!roleRepository.existsById(request.roleId()) || !userRepository.existsById(request.userId())) {
            throw new ResourceNotFoundException("Usuário ou Role inválidos");
        }

        UserRole.UserRoleId id = new UserRole.UserRoleId(request.userId(), request.roleId());
        Optional<UserRole> possibleUserRole = repository.findById(id);
        if (possibleUserRole.isPresent()) {
            throw new AlreadyExistsException("O Usuário já possui a Role");
        }

        UserRole roleToSave = mapperUserRoleRequestToUserRole.map(request);
        UserRole saved = repository.save(roleToSave);
        return mapperUserToUserResponse.map(userRepository.findById(saved.getUserId()).get());
    }

    public void deleteUserRole(Long userId, Long roleId) {
        UserRole.UserRoleId request = new UserRole.UserRoleId(userId, roleId);
        Optional<UserRole> possibleUserRole = repository.findById(request);
        if (!possibleUserRole.isPresent()) {
            throw new AlreadyExistsException("O Usuário não possui a Role");
        }

        repository.deleteById(request);
    }

}
