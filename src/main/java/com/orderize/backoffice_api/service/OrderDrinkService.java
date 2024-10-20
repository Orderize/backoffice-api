package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.order.drink.OrderDrinkRequestDto;
import com.orderize.backoffice_api.dto.order.drink.OrderDrinkResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.drink.OrderDrinkRequestToOrderDrink;
import com.orderize.backoffice_api.mapper.order.drink.OrderDrinkToOrderDrinkResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.OrderDrink;
import com.orderize.backoffice_api.repository.OrderDrinkRepository;
import com.orderize.backoffice_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderDrinkService {
    private final OrderDrinkRepository repository;
    private final OrderRepository orderRepository;
    private final DrinkRepository drinkRepository;

    private final OrderDrinkToOrderDrinkResponse mapperResponse;
    private final OrderDrinkRequestToOrderDrink mapperRequest;

    public OrderDrinkService(
            OrderDrinkRepository repository,
            OrderRepository orderRepository,
            DrinkRepository drinkRepository,
            OrderDrinkToOrderDrinkResponse mapperResponse,
            OrderDrinkRequestToOrderDrink mapperRequest
    ){
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.drinkRepository = drinkRepository;
        this.mapperResponse = mapperResponse;
        this.mapperRequest = mapperRequest;
    }

    public OrderDrinkResponseDto saveOrderDrink(Long orderId, Long drinkId, OrderDrinkRequestDto requestDto){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        Drink drink = drinkRepository.findById(drinkId).orElseThrow(() -> new ResourceNotFoundException("Bebida não encontrada"));

        OrderDrink orderDrinkToSave = mapperRequest.map(requestDto, order, drink);
        repository.save(orderDrinkToSave);
        return mapperResponse.map(orderDrinkToSave);
    }

    public void deleteOrderDrink(Long orderId, Long drinkId){
        OrderDrink.OrderDrinkId id = new OrderDrink.OrderDrinkId(orderId, drinkId);
        OrderDrink orderDrink = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderDrink não encontrado para os IDs fornecidos"));

        repository.deleteById(orderDrink.getId());
    }

}
