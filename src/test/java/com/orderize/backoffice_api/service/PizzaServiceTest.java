package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.pizza.PizzaRequestDtoToPizza;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.repository.FlavorRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;

public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private FlavorRepository flavorRepository;

    @Mock
    private PizzaRequestDtoToPizza pizzaRequestToPizza;

    @Mock
    private PizzaToPizzaResponseDto pizzaToPizzaResponseDto;

    @Mock
    private Flavor flavor;

    @InjectMocks
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar uma Pizza válida")
    void testSavePizza_Success() {
        PizzaRequestDto requestDto = new PizzaRequestDto("Pepperoni", new BigDecimal("25.99"), "Delicious pizza", 1L, "Catupiry", "Pequena", "Fina");
        Pizza pizza = new Pizza(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza");
        List<FlavorResponseDto>  flavors = List.of();
        PizzaResponseDto responseDto = new PizzaResponseDto(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza", flavors, "Catupiry", "Pequena", "Fina");

        when(flavorRepository.findById(requestDto.flavor())).thenReturn(Optional.of(flavor));
        when(pizzaRequestToPizza.map(requestDto, flavor)).thenReturn(pizza);
        when(pizzaRepository.save(pizza)).thenReturn(pizza);
        when(pizzaToPizzaResponseDto.map(pizza)).thenReturn(responseDto);

        PizzaResponseDto result = pizzaService.savePizza(requestDto);

        assertEquals(1L, result.id());
        assertEquals("Pepperoni", result.name());
        assertEquals(flavors, result.flavors());

        verify(flavorRepository).findById(requestDto.flavor());
        verify(pizzaRequestToPizza).map(requestDto, flavor);
        verify(pizzaRepository).save(pizza);
        verify(pizzaToPizzaResponseDto).map(pizza);
    }

    @Test
    @DisplayName("Ao buscar todas as Pizzas")
    void testGetAllPizzas_Success() {
        Pizza pizza1 = new Pizza(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza");
        Pizza pizza2 = new Pizza(2L, "Margherita", new BigDecimal("30.00"), "Classic pizza");
        List<FlavorResponseDto> flavors = List.of();
        PizzaResponseDto responseDto1 = new PizzaResponseDto(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza", flavors, "Catupiry", "Pequena", "Fina");
        PizzaResponseDto responseDto2 = new PizzaResponseDto(2L, "Margherita", new BigDecimal("30.00"), "Classic pizza", flavors, "Catupiry", "Pequena", "Fina");

        when(pizzaRepository.findAll()).thenReturn(List.of(pizza1, pizza2));
        when(pizzaToPizzaResponseDto.map(pizza1)).thenReturn(responseDto1);
        when(pizzaToPizzaResponseDto.map(pizza2)).thenReturn(responseDto2);

        List<PizzaResponseDto> pizzas = pizzaService.getAllPizzas();

        assertEquals(2, pizzas.size());
        assertEquals("Pepperoni", pizzas.get(0).name());
        assertEquals("Margherita", pizzas.get(1).name());
        assertEquals(flavors, pizzas.get(0).flavors());
        verify(pizzaRepository).findAll();
    }

    @Test
    @DisplayName("Ao buscar uma Pizza pelo ID com sucesso")
    void testGetPizzaById_Success() {
        Pizza pizza = new Pizza(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza");
        List<FlavorResponseDto> flavors = List.of();
        PizzaResponseDto responseDto = new PizzaResponseDto(1L, "Pepperoni", new BigDecimal("25.99"), "Delicious pizza", flavors, "Catupiry", "Pequena", "Fina");

        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaToPizzaResponseDto.map(pizza)).thenReturn(responseDto);

        PizzaResponseDto result = pizzaService.getPizzaById(1L);

        assertEquals(1L, result.id());
        assertEquals("Pepperoni", result.name());
        assertEquals(flavors, result.flavors());
        verify(pizzaRepository).findById(1L);
    }

    @Test
    @DisplayName("Ao excluir uma Pizza válida")
    void testDeletePizza_Success() {
        when(pizzaRepository.existsById(1L)).thenReturn(true);

        pizzaService.deletePizza(1L);

        verify(pizzaRepository).existsById(1L);
        verify(pizzaRepository).deleteById(1L);
    }



    @Test
    @DisplayName("Ao salvar uma Pizza inválida")
    void testSavePizza_Invalid() {
        PizzaRequestDto invalidRequestDto = new PizzaRequestDto(null, null, null, null, null, null, null);

        assertThrows(IllegalArgumentException.class, () -> pizzaService.savePizza(invalidRequestDto));
        verify(pizzaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar uma Pizza por ID inexistente")
    void testGetPizzaById_NotFound() {
        when(pizzaRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pizzaService.getPizzaById(999L));
        verify(pizzaRepository).findById(999L);
    }

    @Test
    @DisplayName("Ao excluir uma Pizza que não existe")
    void testDeletePizza_NotFound() {
        when(pizzaRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> pizzaService.deletePizza(999L));
        verify(pizzaRepository).existsById(999L);
        verify(pizzaRepository, never()).deleteById(999L);
    }

    @Test
    @DisplayName("Falha ao buscar todas as Pizzas")
    void testGetAllPizzas_Failure() {
        when(pizzaRepository.findAll()).thenThrow(new RuntimeException("Erro ao acessar o banco de dados"));

        assertThrows(RuntimeException.class, () -> pizzaService.getAllPizzas());
        verify(pizzaRepository).findAll();
    }
}
