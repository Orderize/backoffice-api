package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.OrderRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @Mock
    private OrderRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRequestToOrder mapperRequestToEntity;

    @Mock
    private OrderToOrderResponse mapperEntityToResponse;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao salvar um pedido válido")
    void testSaveOrder(){
        User client = new User(2L, "Maria", new ArrayList<>());
        User responsible = new User(1L, "João", new ArrayList<>());

        OrderRequestDto requestDto = new OrderRequestDto(2L, 1L, "delivery",10.5, 40.0);
        Order order = new Order(1L, client, responsible, "delivery",10.5, 40.0);
        OrderResponseDto responseDto = new OrderResponseDto(1L, 2L, 1L, "delivery",10.5, 40.0);

        when(userRepository.findById(2L)).thenReturn(Optional.of(client));
        when(userRepository.findById(1L)).thenReturn(Optional.of(responsible));

        when(mapperRequestToEntity.map(requestDto, client, responsible)).thenReturn(order);
        when(repository.save(order)).thenReturn(order);
        when(mapperEntityToResponse.map(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.saveOrder(requestDto);

        assertEquals(1L, result.id());
        assertEquals(2L, result.client());
        assertEquals(1L, result.responsible());
        assertEquals("delivery", result.type());
        assertEquals(10.5, result.freight());
        assertEquals(40.0, result.estimativeTime());

        verify(mapperRequestToEntity).map(requestDto, client, responsible);
        verify(repository).save(order);
        verify(mapperEntityToResponse).map(order);
    }

    @Test
    @DisplayName("Ao salvar um pedido inválido")
    void testSaveOrder_Invalid(){
        OrderRequestDto invalidRequestDto = new OrderRequestDto(999L, 999L, null, null, null);
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.saveOrder(invalidRequestDto);
        });

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao atualizar um pedido válido")
    void testUpdateOrder_Sucess(){
        User client = new User(2L, "Maria", new ArrayList<>());
        User responsible = new User(1L, "João", new ArrayList<>());

        OrderRequestDto requestDto = new OrderRequestDto(1L, 2L, 1L, "delivery",10.5, 40.0);
        Order existingOrder = new Order(1L, client, responsible,"delivery",10.5, 40.0);
        Order updatedOrder = new Order(1L, client, responsible, "delivery",10.5, 40.0);
        OrderResponseDto responseDto = new OrderResponseDto(1L, 1L, 2L, "delivery",10.5, 40.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(userRepository.findById(2L)).thenReturn(Optional.of(client)); // Mock do cliente
        when(userRepository.findById(1L)).thenReturn(Optional.of(responsible));

        when(mapperRequestToEntity.map(requestDto, client, responsible)).thenReturn(updatedOrder);
        when(repository.save(updatedOrder)).thenReturn(updatedOrder);
        when(mapperEntityToResponse.map(updatedOrder)).thenReturn(responseDto);

        OrderResponseDto result = orderService.updateOrder(requestDto);

        assertEquals(1L, result.id());
        verify(repository).findById(1L);
        verify(userRepository).findById(2L);
        verify(userRepository).findById(1L);
        verify(repository).save(updatedOrder);
    }

    @Test
    @DisplayName("Ao atualizar um pedido que não existe")
    void testUpdateOrder_NotFound(){
        OrderRequestDto requestDto = new OrderRequestDto(1L, 2L, 1L, "delivery",10.5, 40.0);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.updateOrder(requestDto);
        });

        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(repository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao deletar um pedido válido")
    void testDeleteOrder_Sucess(){
        User client = new User(2L, "Maria", new ArrayList<>());
        User responsible = new User(1L, "João", new ArrayList<>());
        Order existingOrder = new Order(1L, client, responsible,"delivery", 10.5, 40.0);

        when(repository.findById(1L)).thenReturn(Optional.of(existingOrder));
        orderService.deleteOrder(1L);

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
    void testGetOrderById_Sucess(){
        User client = new User(2L, "Maria", new ArrayList<>());
        User responsible = new User(1L, "João", new ArrayList<>());
        Order order = new Order(1L, client, responsible, "delivery",10.5, 40.0);
        OrderResponseDto responseDto = new OrderResponseDto(1L, 2L, 1L, "delivery",10.5, 40.0);

        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(mapperEntityToResponse.map(order)).thenReturn(responseDto);

        OrderResponseDto result = orderService.getOrderById(1L);

        assertEquals(1L, result.id());
        assertEquals("delivery", result.type());
        verify(repository).findById(1L);
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
        User client = new User(2L, "Maria", new ArrayList<>());
        User responsible = new User(1L, "João", new ArrayList<>());

        Order order1 = new Order(1L, client, responsible, "delivery", 10.5, 40.0);
        Order order2 = new Order(2L, client, responsible,"saloon", 12.0, 45.0);
        OrderResponseDto responseDto1 = new OrderResponseDto(1L, 2L, 1L, "delivery", 10.5, 40.0);
        OrderResponseDto responseDto2 = new OrderResponseDto(1L, 3L, 1L, "saloon", 12.0, 45.0);

        when(repository.findAll()).thenReturn(List.of(order1, order2));
        when(mapperEntityToResponse.map(order1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(order2)).thenReturn(responseDto2);

        List<OrderResponseDto> orders = orderService.getAllOrders(null);

        assertEquals(2, orders.size());
        assertEquals("delivery", orders.get(0).type());
        assertEquals("saloon", orders.get(1).type());
        verify(repository).findAll();
    }
}
