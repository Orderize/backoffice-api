package com.orderize.backoffice_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.orderize.backoffice_api.dto.role.RoleRequestDto;
import com.orderize.backoffice_api.dto.role.RoleResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.RoleRequestToRole;
import com.orderize.backoffice_api.mapper.RoleToRoleResponse;
import com.orderize.backoffice_api.model.Role;
import com.orderize.backoffice_api.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

public class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @Mock
    private RoleRequestToRole mapperRoleRequestToRole;

    @Mock
    private RoleToRoleResponse mapperRoleToRoleResponse;

    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar uma Role válida")
    void testSaveRole_Success() {
        RoleRequestDto requestDto = new RoleRequestDto("KOTLINATOR");
        Role role = new Role(1L, "KOTLINATOR");
        RoleResponseDto responseDto = new RoleResponseDto(1L, "KOTLINATOR");

        when(repository.existsByName("KOTLINATOR")).thenReturn(false);
        when(mapperRoleRequestToRole.map(requestDto)).thenReturn(role);
        when(repository.save(role)).thenReturn(role);
        when(mapperRoleToRoleResponse.map(role)).thenReturn(responseDto);

        RoleResponseDto result = roleService.saveRole(requestDto);

        assertEquals(1L, result.id());
        assertEquals("KOTLINATOR", result.name());
        verify(repository).existsByName("KOTLINATOR");
        verify(repository).save(role);
    }

    @Test
    @DisplayName("Ao salvar uma Role que já existe")
    void testSaveRole_AlreadyExists() {
        RoleRequestDto requestDto = new RoleRequestDto("KOTLINATOR");

        when(repository.existsByName("KOTLINATOR")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            roleService.saveRole(requestDto);
        });

        assertEquals("A role que está tentando criar já existe", exception.getMessage());
        verify(repository).existsByName("KOTLINATOR");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar todas as Roles")
    void testGetAllRoles_Success() {
        Role role1 = new Role(1L, "PIZZAIOLO");
        Role role2 = new Role(2L, "SALAO");
        RoleResponseDto responseDto1 = new RoleResponseDto(1L, "PIZZAIOLO");
        RoleResponseDto responseDto2 = new RoleResponseDto(2L, "SALAO");

        when(repository.findAll()).thenReturn(List.of(role1, role2));
        when(mapperRoleToRoleResponse.map(role1)).thenReturn(responseDto1);
        when(mapperRoleToRoleResponse.map(role2)).thenReturn(responseDto2);

        List<RoleResponseDto> roles = roleService.getAllRoles();

        assertEquals(2, roles.size());
        assertEquals("PIZZAIOLO", roles.get(0).name());
        assertEquals("SALAO", roles.get(1).name());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Ao atualizar uma Role válida")
    void testUpdateRole_Success() {
        RoleRequestDto requestDto = new RoleRequestDto("PIZZAIOLO");
        Role updatedRole = new Role(1L, "PIZZAIOLO");
        RoleResponseDto responseDto = new RoleResponseDto(1L, "PIZZAIOLO");

        when(repository.existsById(1L)).thenReturn(true);
        when(mapperRoleRequestToRole.map(requestDto)).thenReturn(updatedRole);
        when(repository.save(updatedRole)).thenReturn(updatedRole);
        when(mapperRoleToRoleResponse.map(updatedRole)).thenReturn(responseDto);

        RoleResponseDto result = roleService.updateRole(1L, requestDto);

        assertEquals(1L, result.id());
        assertEquals("PIZZAIOLO", result.name());
        verify(repository).existsById(1L);
        verify(repository).save(updatedRole);
    }

    @Test
    @DisplayName("Ao atualizar uma Role que não existe")
    void testUpdateRole_NotFound() {
        RoleRequestDto requestDto = new RoleRequestDto("KOTLINATOR");

        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            roleService.updateRole(1L, requestDto);
        });

        assertEquals("Role não encontrada", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao excluir uma Rola válida")
    void testDeleteRole_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        roleService.deleteRole(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma Role que não existe")
    void testDeleteRole_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            roleService.deleteRole(1L);
        });

        assertEquals("Role não encontrada", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(1L);
    }
}