package com.orderize.backoffice_api.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderize.backoffice_api.dto.UserRoleRequestDto;
import com.orderize.backoffice_api.dto.user.UserRequestDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.user.UserRequestToUser;
import com.orderize.backoffice_api.mapper.user.UserToUserResponseDto;
import com.orderize.backoffice_api.model.Address;
import com.orderize.backoffice_api.model.Enterprise;
import com.orderize.backoffice_api.model.Role;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.AddressRepository;
import com.orderize.backoffice_api.repository.EnterpriseRepository;
import com.orderize.backoffice_api.repository.RoleRepository;
import com.orderize.backoffice_api.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final UserToUserResponseDto mapperUserToUserResponse;
    private final UserRequestToUser mapperUserRequestToUser;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    public UserService(
            UserRepository repository,
            AddressRepository addressRepository,
            EnterpriseRepository enterpriseRepository,
            UserToUserResponseDto mapperUserToUserResponse,
            UserRequestToUser mapperUserRequestToUser,
            RoleRepository roleRepository,
            EmailService emailService
            ) {
        this.repository = repository;
        this.addressRepository = addressRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.mapperUserToUserResponse = mapperUserToUserResponse;
        this.mapperUserRequestToUser = mapperUserRequestToUser;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
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

//        if (repository.existsByEmail(userRequest.email())) {
//            throw new AlreadyExistsException("Já existe um usuário utilizando este email");
//        }

        if (repository.existsByPhone(userRequest.phone())) {
            throw new AlreadyExistsException("Já existe um usuário utilizando este telefone");
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

            if (userToUpdate.password() != null && !userToUpdate.password().isBlank()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(savingUser.getPassword());
                savingUser.setPassword(encryptedPassword);
            } else {
                savingUser.setPassword(user.get().getPassword());
            }
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

    public List<UserResponseDto> getAllUsers(String phone, String email, Long enterprise, Long role, String name) {
        List<User> allUsers = repository.findAll();

        if (phone != null && !phone.isBlank()) {
            allUsers = allUsers.stream().filter(it -> it.getPhone() != null && it.getPhone().equals(phone)).toList();
        }

        if (email != null && !email.isBlank()) {
            allUsers = allUsers.stream().filter(it -> it.getEmail().equals(email)).toList();
        }

        if (enterprise != null) {
            allUsers = allUsers.stream().filter(it -> it.getEnterprise().getId() == enterprise).toList();
        }

        if (role != null) {
            List<User> filteredUsers = new ArrayList();
            allUsers.forEach(it -> {
                if (it.getRoles().stream().filter(at -> Objects.equals(at.getId(), role)).count() > 0) {
                    filteredUsers.add(it);
                }
            });
            allUsers = filteredUsers;
        }

        if(name != null){
            allUsers = allUsers.stream().filter(it -> it.getName().equals(name)).toList();
        }

        return allUsers.stream().map(it -> mapperUserToUserResponse.map(it)).toList();
    }

    public User saveRoleToUser(UserRoleRequestDto requestDto) {
        User user = repository.findById(requestDto.userId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        Role role = roleRepository.findById(requestDto.roleId())
            .orElseThrow(() -> new ResourceNotFoundException("Role não encontrado"));

        if (user.getRoles().contains(role)) {
            throw new AlreadyExistsException("Usuário já possui essa role");
        }

        user.getRoles().add(role);
        return repository.save(user);
    } 

    public void deleteRoleFromUser(Long userId, Long roleId) {
        User user = repository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role não encontrado"));
        
        if (!user.getRoles().contains(role)) {
            throw new ResourceNotFoundException("Usuário não possui essa role");
        }
    
        user.getRoles().remove(role);
        repository.save(user);
    }
    
    @Transactional
    public void resetPassword(String email) {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isPresent()){
            User user = userOptional.get();

            String newGeneratedPassword = generateRandomPassword();

            user.setPassword(new BCryptPasswordEncoder().encode(newGeneratedPassword));
            repository.save(user);

            emailService.sendGeneratedPasswordEmail(user.getEmail(), newGeneratedPassword);

        }else{
            throw new RuntimeException("Usuário com o e-mail " + email + " não encontrado.");
        }
    }

    private String generateRandomPassword() {
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";
        String OTHER_CHARS = "!@#$%&*()_-+=";

        String PASSWORD_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHARS;
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder("#OR");

        for (int i = 0; i < 6; i++) {
            password.append(PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length())));
        }
        return password.toString();
    }
}
