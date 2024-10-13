package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.OrderRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final UserRepository userRepository;
    private final OrderToOrderResponse mapperOrderToOrderResponse;
    private final OrderRequestToOrder mapperOrderRequestToOrder;

    public OrderService(
            OrderRepository repository,
            UserRepository userRepository,
            OrderToOrderResponse mapperOrderToOrderResponse,
            OrderRequestToOrder mapperOrderRequestToOrder
    ){
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapperOrderToOrderResponse = mapperOrderToOrderResponse;
        this.mapperOrderRequestToOrder = mapperOrderRequestToOrder;
    }

    public OrderResponseDto getOrderById(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return mapperOrderToOrderResponse.map(order);
    }

    public List<OrderResponseDto> getAllOrders(String type){
        List<Order> orders = repository.findAll();
        if (type != null && !type.isBlank()){
            orders = orders.stream().filter(it -> it.getType().equals(type)).toList();
        }
        return orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList();
    }

    @Transactional
    public OrderResponseDto saveOrder(OrderRequestDto orderResquest){
        User client = null;
        User responsible = null;

        if (orderResquest.client() != null){
            client = userRepository.findById(orderResquest.client())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        if (orderResquest.responsible() != null){
            responsible = userRepository.findById(orderResquest.responsible())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        Order orderToSave = mapperOrderRequestToOrder.map(orderResquest, client, responsible);
        repository.save(orderToSave);

        return mapperOrderToOrderResponse.map(orderToSave);
    }

    @Transactional
    public OrderResponseDto updateOrder(OrderRequestDto orderToUpdate){
        Order order = repository.findById(orderToUpdate.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User client = null;
        User responsible = null;

        if (orderToUpdate.client() != null){
            client = userRepository.findById(orderToUpdate.client())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
        if (orderToUpdate.responsible() != null){
            responsible = userRepository.findById(orderToUpdate.responsible())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

        Order savingOrder = mapperOrderRequestToOrder.map(orderToUpdate, client, responsible);
        savingOrder.setId(order.getId());
        return mapperOrderToOrderResponse.map(repository.save(savingOrder));
    }

    @Transactional
    public void deleteOrder(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.deleteById(order.getId());
    }
}
