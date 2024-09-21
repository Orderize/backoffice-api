package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.RoleRequestToRole;
import com.orderize.backoffice_api.mapper.RoleToRoleResponse;
import com.orderize.backoffice_api.model.Role;
import com.orderize.backoffice_api.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;
    private final RoleRequestToRole mapperRoleRequestToRole;
    private final RoleToRoleResponse mapperRoleToRoleResponse;

    public RoleService(RoleRepository repository, RoleRequestToRole mapperRoleRequestToRole, RoleToRoleResponse mapperRoleToRoleResponse) {
        this.repository = repository;
        this.mapperRoleRequestToRole = mapperRoleRequestToRole;
        this.mapperRoleToRoleResponse = mapperRoleToRoleResponse;
    }

    public RoleResponseDto saveRole(RoleRequestDto roleRequest) {
        if (repository.existsByName(roleRequest.name())) {
            throw new AlreadyExistsException("A role que está tentando criar já existe");
        }

        Role savedRole = repository.save(mapperRoleRequestToRole.map(roleRequest));
        return mapperRoleToRoleResponse.map(savedRole);
    }

    public List<RoleResponseDto> getAllRoles() {
        return repository.findAll().stream().map(it -> mapperRoleToRoleResponse.map(it)).toList();
    }

    public RoleResponseDto updateRole(Long id, RoleRequestDto role) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Role não encontrada");
        }

        Role roleToSave = mapperRoleRequestToRole.map(role);
        roleToSave.setId(id);
        Role savedRole = repository.save(roleToSave);
        return mapperRoleToRoleResponse.map(savedRole);
    }


    public void deleteRole(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Role não encontrada");
        }

        repository.deleteById(id);
    }

}
