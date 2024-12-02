package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.repository.FlavorRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PizzaServiceTest {

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private FlavorRepository flavorRepository;

    @Mock
    private Flavor flavor;

    @InjectMocks
    private PizzaService pizzaService;

    private static List<Flavor> flavors;

    private static List<Pizza> pizzas;

    private static Pizza pizza;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flavors = List.of(
                new Flavor(
                        "Marguerita",
                        "Sabor gostoso",
                        BigDecimal.valueOf(128.0)
                ),
                new Flavor(
                        "Mussarela",
                        "Sabor gostoso",
                        BigDecimal.valueOf(130.0)
                ),
                new Flavor(
                        "Frango com catupiry",
                        "Sabor gostoso",
                        BigDecimal.valueOf(12.0)
                )
        );

        pizzas = List.of(
                new Pizza(
                        1L,
                        "Marguerita",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        flavors
                ),
                new Pizza(
                        2L,
                        "JHVJHVV",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        flavors
                ),
                new Pizza(
                        3L,
                        "fsdfsdffreg",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        flavors
                )
        );

        pizza = new Pizza(
                1L,
                "Marguerita",
                BigDecimal.valueOf(128.0),
                "Muitas observacoes",
                flavors
        );
    }

    @Test
    @DisplayName("getAllPizzas quando invocado e não houverem pizzas no banco deve retornar uma lista vazia")
    void getAllPizzasQuandoInvocadoENaoHouveremPizzasNoBancoDeveRetornarUmaListaVazia() {
        when(pizzaRepository.findAll()).thenReturn(Collections.emptyList());

        List<Pizza> result = pizzaService.getAllPizzas();

        assertTrue(result.isEmpty());
        verify(
                pizzaRepository,
                times(1)
        ).findAll();
    }

    @Test
    @DisplayName("getAllPizzas quando invocado e houverem pizzas no banco deve retornar uma lista de pizzas")
    void getAllPizzasQuandoInvocadoEHouveremPizzasNoBancoDeveRetornarUmaListaDePizzas() {
        when(pizzaRepository.findAll()).thenReturn(pizzas);

        List<Pizza> result = pizzaService.getAllPizzas();

        assertFalse(result.isEmpty());
        verify(
                pizzaRepository,
                times(1)
        ).findAll();
    }

    @Test
    @DisplayName("getPizzaById quando invocado com id inválido deve lancar ResourceNotFoundException")
    void getPizzaByIdQuandoInvocadoComIdInvalidoDeveLancarResourceNotFoundException() {
        when(pizzaRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pizzaService.getPizzaById(1L)
        );
        assertEquals("Pizza não encontrada", exception.getMessage());
        verify(
                pizzaRepository,
                times(1)
        ).findById(anyLong());
    }

    @Test
    @DisplayName("getPizzaById quando invocado com id válido deve retornar Pizza")
    void getPizzaByIdQuandoInvocadoComIdValidoDeveRetornarPizza() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));

        Pizza result = pizzaService.getPizzaById(1L);

        assertNotNull(result);
        assertEquals(pizza, result);
        verify(
                pizzaRepository,
                times(1)
        ).findById(anyLong());
    }

    @Test
    @DisplayName("savePizza quando invocado com pizza válida deve salvar e retornar Pizza salva")
    void savePizzaQuandoInvocadoComPizzaValidaDeveSalvarPizza() {
        Pizza pizzaToSave = new Pizza(
                null,
                pizza.getName(),
                pizza.getPrice(),
                pizza.getObservations(),
                pizza.getFlavors()
        );
        pizzaToSave.setId(null);
        when(pizzaRepository.save(pizzaToSave)).thenReturn(pizza);

        Pizza result = pizzaService.savePizza(pizzaToSave);

        assertEquals(1L, result.getId());
        assertEquals(pizzaToSave.getName(), result.getName());
        assertEquals(pizzaToSave.getPrice(), result.getPrice());
        assertEquals(pizzaToSave.getObservations(), result.getObservations());
        assertEquals(pizzaToSave.getFlavors(), result.getFlavors());
        verify(
                pizzaRepository,
                times(1)
        ).save(pizzaToSave);
    }

    @Test
    @DisplayName("updatePizza quando invocado com id válido e request válido deve atualizar a pizza no banco")
    void updatePizzaQuandoInvocadoComIdValidoERequestValidoDeveAtualizarAPizzaNoBanco() {
        Long id = 1L;
        Pizza pizzaToSave = new Pizza(
                null,
                pizza.getName(),
                pizza.getPrice(),
                pizza.getObservations(),
                pizza.getFlavors()
        );
        when(pizzaRepository.existsById(1L)).thenReturn(true);
        when(pizzaRepository.save(pizzaToSave)).thenReturn(pizza);

        Pizza result = pizzaService.updatePizza(1L, pizzaToSave);

        assertEquals(id, result.getId());
        assertEquals(pizzaToSave.getName(), result.getName());
        assertEquals(pizzaToSave.getPrice(), result.getPrice());
        assertEquals(pizzaToSave.getObservations(), result.getObservations());
        assertEquals(pizzaToSave.getFlavors(), result.getFlavors());
        verify(
                pizzaRepository,
                times(1)
        ).existsById(id);
        verify(
                pizzaRepository,
                times(1)
        ).save(pizzaToSave);
    }

    @Test
    @DisplayName("updatePizza quando invocado com id inexistente deve lançar ResourceNotFoundException")
    void updatePizzaQuandoInvocadoComIdInexistenteDeveLancarResourceNotFoundException() {
        Long id = 1L;
        when(pizzaRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pizzaService.updatePizza(1L, pizza)
        );
        assertEquals("Pizza não encontrada", exception.getMessage());
        verify(
                pizzaRepository,
                never()
        ).save(any());
    }

    @Test
    @DisplayName("getPizzaFlavors quando receber ids válidos deve retornar uma lista de flavor")
    void getPizzaFlavorsQuandoReceberIdsValidosDeveRetornarUmaListaDeFlavor() {
        List<Long> ids = List.of(
                1L,
                2L,
                3L
        );
        when(flavorRepository.findAllById(ids)).thenReturn(flavors);

        List<Flavor> result = pizzaService.getPizzaFlavors(ids);

        assertEquals(flavors, result);
        verify(
                flavorRepository,
                times(1)
        ).findAllById(ids);
    }

    @Test
    @DisplayName("getPizzaFlavors quando receber ids inválidos deve retornar uma lista vázia")
    void getPizzaFlavorsQuandoReceberIdsInvalidosDeveRetornarUmaListaVazia() {
        List<Long> ids = List.of(
                1L,
                2L,
                3L
        );
        when(flavorRepository.findAllById(ids)).thenReturn(Collections.emptyList());

        List<Flavor> result = pizzaService.getPizzaFlavors(ids);

        assertEquals(Collections.emptyList(), result);
        verify(
                flavorRepository,
                times(1)
        ).findAllById(ids);
    }

    @Test
    @DisplayName("getPrice quando invocado deve retornar o preço do Flavor com preço mais alto")
    void getPriceQuandoInvocadoDeveRetornarOPrecoDoFlavorComPrecoMaisAlto() {
        BigDecimal expectedPrice = BigDecimal.valueOf(130);

        BigDecimal result = pizzaService.getPrice(flavors);

        assertEquals(expectedPrice.doubleValue(), result.doubleValue());
    }

    @Test
    @DisplayName("getPizzaName quando invocado deve retornar o nome da pizza que será o nome dos Flavors concatenados")
    void getPizzaNameQuandoInvocadoDeveRetornarONomeDaPizzaQueSeraONomeDosFlavorsConcatenados() {
        String expectedName = "Marguerita Mussarela e Frango com catupiry";

        String result = pizzaService.getPizzaName(flavors);

        assertEquals(expectedName, result);
    }

}
