package com.orderize.backoffice_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseRequestDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.enterprise.EnterpriseRequestToEnterprise;
import com.orderize.backoffice_api.mapper.enterprise.EnterpriseToEnterpriseResponse;
import com.orderize.backoffice_api.model.Enterprise;
import com.orderize.backoffice_api.repository.EnterpriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class EnterpriseServiceTest {

    @Mock
    private EnterpriseRepository repository;

    @Mock
    private EnterpriseRequestToEnterprise mapperRequestToEntity;

    @Mock
    private EnterpriseToEnterpriseResponse mapperEntityToResponse;

    @InjectMocks
    private EnterpriseService enterpriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar uma Enterprise válida")
    void testSaveEnterprise_Success() {
        EnterpriseRequestDto requestDto = new EnterpriseRequestDto("Kotlin company", "12345678900");
        Enterprise enterprise = new Enterprise(1L, "Kotlin company", "12345678900");
        EnterpriseResponseDto responseDto = new EnterpriseResponseDto(1L, "Kotlin company", "12345678900");

        when(repository.existsByName("Kotlin company")).thenReturn(false);
        when(repository.existsByCnpj("12345678900")).thenReturn(false);
        when(mapperRequestToEntity.map(requestDto)).thenReturn(enterprise);
        when(repository.save(enterprise)).thenReturn(enterprise);
        when(mapperEntityToResponse.map(enterprise)).thenReturn(responseDto);

        EnterpriseResponseDto result = enterpriseService.saveEnterprise(requestDto);

        assertEquals(1L, result.id());
        assertEquals("Kotlin company", result.name());
        assertEquals("12345678900", result.cnpj());
        verify(repository).existsByName("Kotlin company");
        verify(repository).existsByCnpj("12345678900");
        verify(repository).save(enterprise);
    }

    @Test
    @DisplayName("Ao salvar uma Enterprise que já existe pelo nome")
    void testSaveEnterprise_AlreadyExistsByName() {
        EnterpriseRequestDto requestDto = new EnterpriseRequestDto("Kotlin company", "12345678900");
        Enterprise enterprise = new Enterprise(1L, "Kotlin company", "12345678900");

        when(mapperRequestToEntity.map(requestDto)).thenReturn(enterprise);
        when(repository.existsByName("Kotlin company")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            enterpriseService.saveEnterprise(requestDto);
        });

        assertEquals("Já existe uma empresa com este nome", exception.getMessage());
        verify(repository).existsByName("Kotlin company");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao salvar uma Enterprise que já existe pelo CNPJ")
    void testSaveEnterprise_AlreadyExistsByCnpj() {
        EnterpriseRequestDto requestDto = new EnterpriseRequestDto("Kotlin company", "12345678900");
        Enterprise enterprise = new Enterprise(1L, "Kotlin company", "12345678900");

        when(mapperRequestToEntity.map(requestDto)).thenReturn(enterprise);
        when(repository.existsByCnpj("12345678900")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            enterpriseService.saveEnterprise(requestDto);
        });

        assertEquals("Já existe uma empresa com este CNPJ", exception.getMessage());
        verify(repository).existsByCnpj("12345678900");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar todas as Enterprises")
    void testGetAllEnterprises_Success() {
        Enterprise enterprise1 = new Enterprise(1L, "Kotlin company", "12345678900");
        Enterprise enterprise2 = new Enterprise(2L, "Android company", "09876543211");
        EnterpriseResponseDto responseDto1 = new EnterpriseResponseDto(1L, "Kotlin company", "12345678900");
        EnterpriseResponseDto responseDto2 = new EnterpriseResponseDto(2L, "Android company", "09876543211");

        when(repository.findAll()).thenReturn(List.of(enterprise1, enterprise2));
        when(mapperEntityToResponse.map(enterprise1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(enterprise2)).thenReturn(responseDto2);

        List<EnterpriseResponseDto> enterprises = enterpriseService.getAllEnterprises(null);

        assertEquals(2, enterprises.size());
        assertEquals("Kotlin company", enterprises.get(0).name());
        assertEquals("Android company", enterprises.get(1).name());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Ao buscar uma Enterprise pelo ID com sucesso")
    void testGetEnterpriseById_Success() {
        Enterprise enterprise = new Enterprise(1L, "Kotlin company", "12345678900");
        EnterpriseResponseDto responseDto = new EnterpriseResponseDto(1L, "Kotlin company", "12345678900");

        when(repository.findById(1L)).thenReturn(Optional.of(enterprise));
        when(mapperEntityToResponse.map(enterprise)).thenReturn(responseDto);

        EnterpriseResponseDto result = enterpriseService.getEnterpriseById(1L);

        assertEquals(1L, result.id());
        assertEquals("Kotlin company", result.name());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Ao buscar uma Enterprise pelo ID inexistente")
    void testGetEnterpriseById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            enterpriseService.getEnterpriseById(1L);
        });

        assertEquals("Empresa não encontrada", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Ao atualizar uma Enterprise válida")
    void testUpdateEnterprise_Success() {
        EnterpriseRequestDto requestDto = new EnterpriseRequestDto("Kotlin company", "12345678900");
        Enterprise updatedEnterprise = new Enterprise(1L, "Kotlin company", "12345678900");
        EnterpriseResponseDto responseDto = new EnterpriseResponseDto(1L, "Kotlin company", "12345678900");

        when(repository.existsById(1L)).thenReturn(true);
        when(mapperRequestToEntity.map(requestDto)).thenReturn(updatedEnterprise);
        when(repository.save(updatedEnterprise)).thenReturn(updatedEnterprise);
        when(mapperEntityToResponse.map(updatedEnterprise)).thenReturn(responseDto);

        EnterpriseResponseDto result = enterpriseService.updateEnterprise(1L, requestDto);

        assertEquals(1L, result.id());
        assertEquals("Kotlin company", result.name());
        verify(repository).existsById(1L);
        verify(repository).save(updatedEnterprise);
    }

    @Test
    @DisplayName("Ao atualizar uma Enterprise que não existe")
    void testUpdateEnterprise_NotFound() {
        EnterpriseRequestDto requestDto = new EnterpriseRequestDto("Kotlin company", "12345678900");

        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            enterpriseService.updateEnterprise(1L, requestDto);
        });

        assertEquals("Empresa não encontrada", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao excluir uma Enterprise válida")
    void testDeleteEnterprise_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        enterpriseService.deleteEnterprise(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma Enterprise que não existe")
    void testDeleteEnterprise_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            enterpriseService.deleteEnterprise(1L);
        });

        assertEquals("Empresa não encontrada", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(1L);
    }
}
