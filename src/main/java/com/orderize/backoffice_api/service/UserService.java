package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.user.UserRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.mapper.UserRequestToUser;
import com.orderize.backoffice_api.mapper.UserToUserResponseDto;
import com.orderize.backoffice_api.model.Address;
import com.orderize.backoffice_api.model.Enterprise;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.AddressRepository;
import com.orderize.backoffice_api.repository.EnterpriseRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final UserToUserResponseDto mapperUserToUserResponse;
    private final UserRequestToUser mapperUserRequestToUser;

    public UserService(
            UserRepository repository,
            AddressRepository addressRepository,
            EnterpriseRepository enterpriseRepository,
            UserToUserResponseDto mapperUserToUserResponse,
            UserRequestToUser mapperUserRequestToUser
    ) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.mapperUserToUserResponse = mapperUserToUserResponse;
        this.mapperUserRequestToUser = mapperUserRequestToUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        return user;
    }

    public UserResponseDto getUserById(Long id) {
        Optional<User> user = repository.findById(id);

        if (user.isPresent()) {
            return mapperUserToUserResponse.map(user.get());
        } else {
            return null;
        }
    }

    public UserResponseDto saveUser(UserRequestDto userRequest) {
        Address address = null;
        Enterprise enterprise = null;
        if (userRequest.address() != null) {
            address = addressRepository.findById(userRequest.address())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
        }
        if (userRequest.enterprise() != null) {
            enterprise = enterpriseRepository.findById(userRequest.enterprise())
                    .orElseThrow(() -> new RuntimeException("Enterprise not found"));
        }

        if (repository.existsByEmail(userRequest.email())) {
            throw new AlreadyExistsException("Já existe um usuário utilizando este email");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRequest.password());
        User userToSave = mapperUserRequestToUser.map(userRequest, address, enterprise);
        userToSave.setPassword(encryptedPassword);
        Optional<User> user = Optional.of(repository.save(userToSave));

        if (user.isPresent()) {
            return mapperUserToUserResponse.map(user.get());
        } else {
            return null;
        }
    }

    public UserResponseDto updateUser(UserRequestDto userToUpdate) {
        Optional<User> user = repository.findById(userToUpdate.id());

        Address address = null;
        Enterprise enterprise = null;
        if (userToUpdate.address() != null) {
            address = addressRepository.findById(userToUpdate.address())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
        }
        if (userToUpdate.enterprise() != null) {
            enterprise = enterpriseRepository.findById(userToUpdate.enterprise())
                    .orElseThrow(() -> new RuntimeException("Enterprise not found"));
        }

        if (user.isPresent()) {
            User savingUser = mapperUserRequestToUser.map(userToUpdate, address, enterprise);
            savingUser.setId(user.get().getId());
            String encryptedPassword = new BCryptPasswordEncoder().encode(savingUser.getPassword());
            savingUser.setPassword(encryptedPassword);
            return mapperUserToUserResponse.map(repository.save(savingUser));
        } else {
            return null;
        }
    }

    public Boolean deleteUser(Long id) {
        Optional<User> us = repository.findById(id);

        if (us.isPresent()) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
