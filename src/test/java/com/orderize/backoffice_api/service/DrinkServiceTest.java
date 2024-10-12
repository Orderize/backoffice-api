package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.drink.DrinkRequestDto;
import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.DrinkRequestToDrink;
import com.orderize.backoffice_api.mapper.DrinkToDrinkResponse;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.repository.DrinkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrinkServiceTest {

    @Mock
    private DrinkRepository repository;

    @Mock
    private DrinkRequestToDrink mapperRequestToEntity;

    @Mock
    private DrinkToDrinkResponse mapperEntityToResponse;

    @InjectMocks
    private DrinkService drinkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar uma bebida válida")
    void testSaveDrink_Success() {
        DrinkRequestDto requestDto = new DrinkRequestDto("Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        Drink drink = new Drink(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        DrinkResponseDto responseDto = new DrinkResponseDto(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);

        when(repository.existsByName("Coca Cola")).thenReturn(false);
        when(mapperRequestToEntity.map(requestDto)).thenReturn(drink);
        when(repository.save(drink)).thenReturn(drink);
        when(mapperEntityToResponse.map(drink)).thenReturn(responseDto);

        DrinkResponseDto result = drinkService.saveDrink(requestDto);

        assertEquals(1L, result.id());
        assertEquals("Coca Cola", result.name());
        assertEquals("Refrigerante de cola", result.description());
        assertEquals(new BigDecimal("5.99"), result.price());
        assertEquals(350, result.milimeters());
        verify(repository).existsByName("Coca Cola");
        verify(repository).save(drink);
    }

    @Test
    @DisplayName("Ao salvar uma bebida que já existe pelo nome")
    void testSaveDrink_AlreadyExists() {
        DrinkRequestDto requestDto = new DrinkRequestDto("Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        when(repository.existsByName("Coca Cola")).thenReturn(true);

        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class, () -> {
            drinkService.saveDrink(requestDto);
        });

        assertEquals("Essa bebida já existe no sistema", exception.getMessage());
        verify(repository).existsByName("Coca Cola");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar todas as bebidas")
    void testGetAllDrinks_Success() {
        Drink drink1 = new Drink(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        Drink drink2 = new Drink(2L, "Pepsi", "Refrigerante de cola", new BigDecimal("4.99"), 350);
        DrinkResponseDto responseDto1 = new DrinkResponseDto(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        DrinkResponseDto responseDto2 = new DrinkResponseDto(2L, "Pepsi", "Refrigerante de cola", new BigDecimal("4.99"), 350);

        when(repository.findAll()).thenReturn(List.of(drink1, drink2));
        when(mapperEntityToResponse.map(drink1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(drink2)).thenReturn(responseDto2);

        List<DrinkResponseDto> drinks = drinkService.getAllDrinks("", null);

        assertEquals(2, drinks.size());
        assertEquals("Coca Cola", drinks.get(0).name());
        assertEquals("Pepsi", drinks.get(1).name());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Ao atualizar uma bebida válida")
    void testUpdateDrink_Success() {
        Drink actualDrink = new Drink(1L, "Dolly Cola", "Refrigerante de cola", new BigDecimal("100.99"), 350);
        DrinkRequestDto requestDto = new DrinkRequestDto("Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        Drink updatedDrink = new Drink(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);
        DrinkResponseDto responseDto = new DrinkResponseDto(1L, "Coca Cola", "Refrigerante de cola", new BigDecimal("5.99"), 350);

        when(repository.findById(1L)).thenReturn(Optional.of(actualDrink));
        when(mapperRequestToEntity.map(requestDto)).thenReturn(updatedDrink);
        when(repository.save(updatedDrink)).thenReturn(updatedDrink);
        when(mapperEntityToResponse.map(updatedDrink)).thenReturn(responseDto);

        DrinkResponseDto result = drinkService.updateDrink(1L, requestDto);

        assertEquals(1L, result.id());
        assertEquals("Coca Cola", result.name());
        assertEquals("Refrigerante de cola", result.description());
        verify(repository).findById(1L);
        verify(repository).save(updatedDrink);
    }

    @Test
    @DisplayName("Ao excluir uma bebida válida")
    void testDeleteDrink_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        drinkService.deleteDrink(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma bebida que não existe")
    void testDeleteDrink_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            drinkService.deleteDrink(1L);
        });

        assertEquals("Bebida para deletar não encontrada", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).deleteById(1L);
    }
}
