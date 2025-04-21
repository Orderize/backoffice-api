package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.DrinkRepository;
import com.orderize.backoffice_api.repository.OrderRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import com.orderize.backoffice_api.util.observer.order_attestation.OrderObserver;

public class OrderServiceTest {
    @Mock
    private OrderRepository repository;

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private OrderRequestToOrder mapperRequestToEntity;

    @Mock
    private OrderToOrderResponse mapperEntityToResponse;

    @Mock
    private User client;

    @Mock
    private User responsible;

    @Mock
    private List<Pizza> pizzas;

    @Mock
    private List<Drink> drinks;

    @Mock
    private UserResponseDto clientDto;

    @Mock
    private UserResponseDto responsibleDto;

    @Mock
    private List<PizzaResponseDto> pizzasDto;

    @Mock
    private List<DrinkResponseDto> drinksDto;

    private List<OrderObserver> observers;

    @Mock
    private OrderObserver observer1;

    @Mock
    private OrderObserver observer2;


    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        observers = new ArrayList<>();
        ReflectionTestUtils.setField(orderService, "observers", observers);
    }

    @Test
    @DisplayName("Ao salvar um pedido válido")
    void testSaveOrder(){
        OrderRequestDto requestDto = new OrderRequestDto(2L, 1L, List.of(1L, 2L, 3L), List.of(1L, 2L, 3L), "delivery", BigDecimal.valueOf(45.0), 50, null, "PENDENTE");
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(drinks)
            .datetime(Instant.now())
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        OrderResponseDto responseDto = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, drinksDto, Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());

        when(userRepository.findById(requestDto.client())).thenReturn(Optional.of(client));
        when(userRepository.findById(requestDto.responsible())).thenReturn(Optional.of(responsible));
        when(pizzaRepository.findAllById(requestDto.pizzas())).thenReturn(pizzas);
        when(drinkRepository.findAllById(requestDto.drinks())).thenReturn(drinks);
        when(mapperRequestToEntity.map(requestDto)).thenReturn(order);
        when(repository.save(order)).thenReturn(order);
        when(mapperEntityToResponse.map(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.saveOrder(requestDto);

        assertEquals(responseDto.id(), result.id());
        assertEquals(responseDto.client(), result.client());
        assertEquals(responseDto.responsible(), result.responsible());
        assertEquals(responseDto.pizzas(), result.pizzas());
        assertEquals(responseDto.drinks(), result.drinks());
        assertEquals(responseDto.type(), result.type());
        assertEquals(responseDto.freight(), result.freight());
        assertEquals(responseDto.estimatedTime(), result.estimatedTime());

        verify(userRepository).findById(requestDto.client());
        verify(userRepository).findById(requestDto.responsible());
        verify(pizzaRepository).findAllById(requestDto.pizzas());
        verify(drinkRepository).findAllById(requestDto.drinks());
        verify(mapperRequestToEntity).map(requestDto);
        verify(repository, times(2)).save(order);
        verify(mapperEntityToResponse).map(order);
    }

    @Test
    @DisplayName("Ao salvar um pedido inválido")
    void testSaveOrder_Invalid(){
        OrderRequestDto invalidRequestDto = new OrderRequestDto(null, null, null, null, null, null, null, null, null);
        
        assertThrows(ResourceNotFoundException.class, () -> { 
            orderService.saveOrder(invalidRequestDto);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao atualizar um pedido válido")
    void testUpdateOrder_Sucess(){
        OrderRequestDto requestDto = new OrderRequestDto(2L, 1L, List.of(1L, 2L, 3L), List.of(1L, 2L, 3L), "delivery", BigDecimal.valueOf(45.0), 50, null, "PENDENTE");
        Order updatedOrder = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        OrderResponseDto responseDto = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, drinksDto, Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());


        when(repository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(requestDto.client())).thenReturn(Optional.of(client));
        when(userRepository.findById(requestDto.responsible())).thenReturn(Optional.of(responsible));
        when(pizzaRepository.findAllById(requestDto.pizzas())).thenReturn(pizzas);
        when(mapperRequestToEntity.map(requestDto)).thenReturn(updatedOrder);
        when(repository.save(updatedOrder)).thenReturn(updatedOrder);
        when(mapperEntityToResponse.map(updatedOrder)).thenReturn(responseDto);

        OrderResponseDto result = orderService.updateOrder(requestDto, 1L);

        assertEquals(responseDto.id(), result.id());
        assertEquals(responseDto.client(), result.client());
        assertEquals(responseDto.responsible(), result.responsible());
        assertEquals(responseDto.pizzas(), result.pizzas());
        assertEquals(responseDto.drinks(), result.drinks());

        verify(repository).existsById(1L);
        verify(userRepository).findById(requestDto.client());
        verify(userRepository).findById(requestDto.responsible());
        verify(pizzaRepository).findAllById(requestDto.pizzas());
        verify(mapperRequestToEntity).map(requestDto);
        verify(repository).save(updatedOrder);
        verify(mapperEntityToResponse).map(updatedOrder);
    }

    @Test
    @DisplayName("Ao atualizar um pedido que não existe")
    void testUpdateOrder_NotFound(){
        OrderRequestDto requestDto = new OrderRequestDto(2L, 1L, List.of(1L, 2L, 3L), null, "delivery", BigDecimal.valueOf(45.0), 50, null, "PENDENTE");

        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.updateOrder(requestDto, 1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(repository).existsById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao deletar um pedido válido")
    void testDeleteOrder_Sucess(){
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();

        when(repository.findById(1L)).thenReturn(Optional.of(order));
        orderService.deleteOrder(order.getId());

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao deletar um pedido que não existe")
    void testDeleteOrder_NotFound(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao buscar um pedido pelo ID com sucesso")
    void testGetOrderById_Success(){
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        OrderResponseDto responseDto = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, List.of(), Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());

        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(mapperEntityToResponse.map(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.getOrderById(1L);

        assertEquals(responseDto.id(), result.id());
        assertEquals(responseDto.client(), result.client());
        assertEquals(responseDto.responsible(), result.responsible());
        assertEquals(responseDto.pizzas(), result.pizzas());
        assertEquals(responseDto.drinks(), result.drinks());
        assertEquals("delivery", result.type());

        verify(repository).findById(1L);
        verify(mapperEntityToResponse).map(order);
    }

    @Test
    @DisplayName("Ao buscar um pedido por um ID inexistente")
    void testGetOrderById_NotFound(){
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Ao buscar todos os pedidos")
    void testGetAllOrders_Sucess() {
        Order order1 = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        Order order2 = Order.builder()
            .id(2L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now())
            .type("delivery")
            .freight(BigDecimal.valueOf(43.0))
            .estimatedTime(63)
            .price(BigDecimal.valueOf(56.0))
            .status("PENDENTE")
        .build();
        
        OrderResponseDto responseDto1 = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, List.of(), Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());
        OrderResponseDto responseDto2 = new OrderResponseDto(2L, clientDto, responsibleDto, pizzasDto, drinksDto, Instant.now(), "delivery", BigDecimal.valueOf(43.0), 63, BigDecimal.valueOf(56.0), null, "PENDENTE", Instant.now());

        when(repository.findAll()).thenReturn(List.of(order1, order2));
        when(mapperEntityToResponse.map(order1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(order2)).thenReturn(responseDto2);

        List<OrderResponseDto> orders = orderService.getAllOrders(null);

        assertEquals(2, orders.size());
        assertEquals("delivery", orders.get(0).type());
        assertEquals("delivery", orders.get(1).type());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Ao buscar todos os pedidos filtrados")
    void testGetAllOrdersFiltered_Sucess() {
        Order order1 = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        Order order2 = Order.builder()
            .id(2L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now())
            .type("saloon")
            .freight(BigDecimal.valueOf(43.0))
            .estimatedTime(63)
            .price(BigDecimal.valueOf(56.0))
            .status("PENDENTE")
            .table(1)
        .build();
        
        OrderResponseDto responseDto1 = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, List.of(), Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());
        OrderResponseDto responseDto2 = new OrderResponseDto(2L, clientDto, responsibleDto, pizzasDto, drinksDto, Instant.now(), "saloon", BigDecimal.valueOf(43.0), 63, BigDecimal.valueOf(56.0), 1, "PENDENTE", Instant.now());

        when(repository.findAll()).thenReturn(List.of(order1, order2));
        when(mapperEntityToResponse.map(order1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(order2)).thenReturn(responseDto2);

        List<OrderResponseDto> orders = orderService.getAllOrders("Saloon");

        assertEquals(1, orders.size());
        assertEquals("saloon", orders.get(0).type());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Adicionar observer")
    void addObserver() {
        orderService.addObserver(observer1);
        assertTrue(observers.contains(observer1));
    }

    @Test
    @DisplayName("Remover observer")
    void testRemoveObserver() {
        observers.add(observer1);
        observers.add(observer2);

        orderService.removeObserver(observer1);
        
        assertFalse(observers.contains(observer1));
        assertTrue(observers.contains(observer2));
    }

    @Test
    @DisplayName("Notificar observers")
    void testNotifyObservers() {
        observers.add(observer1);
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(drinks)
            .datetime(Instant.now())
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();

        orderService.notifyObservers(order);
        
        verify(observer1).onOrderCreated(order);        
    }

    @Test
    @DisplayName("Pegar os últimos pedidos em ordem")
    void testGetLastOrders() {
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        OrderResponseDto responseDto = new OrderResponseDto(1L, clientDto, responsibleDto, pizzasDto, List.of(), Instant.now(), "delivery", BigDecimal.valueOf(45.0), 50, BigDecimal.valueOf(45.0), null, "PENDENTE", Instant.now());
        Instant datetime = Instant.now();


        when(repository.findByDatetimeBeforeOrderByDatetimeAsc(datetime)).thenReturn(List.of(order));
        when(mapperEntityToResponse.map(order)).thenReturn(responseDto);

        List<OrderResponseDto> result = orderService.getLastOrders(datetime);

        assertEquals(responseDto.id(), result.get(0).id());
        assertEquals(responseDto.client(), result.get(0).client());
        assertEquals(responseDto.responsible(), result.get(0).responsible());
        assertEquals(responseDto.pizzas(), result.get(0).pizzas());
        assertEquals(responseDto.drinks(), result.get(0).drinks());
        assertEquals("delivery", result.get(0).type());

        verify(repository).findByDatetimeBeforeOrderByDatetimeAsc(datetime);
        verify(mapperEntityToResponse).map(order);
    }

    @Test
    @DisplayName("Calcula o preço do pedido")
    void calculateOrderPrices(){
        Order order = Order.builder()
            .id(1L)
            .client(client)
            .responsible(responsible)
            .pizzas(pizzas)
            .drinks(List.of())
            .datetime(Instant.now().minus(Duration.ofHours(3)))
            .type("delivery")
            .freight(BigDecimal.valueOf(45.0))
            .estimatedTime(50)
            .price(BigDecimal.valueOf(45.0))
            .status("PENDENTE")
        .build();
        
        orderService.calculateOrderPrices(order);
        
        assertEquals(order.getPrice(), BigDecimal.valueOf(45.0));
    }
}

