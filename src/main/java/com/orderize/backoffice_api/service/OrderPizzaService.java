package com.orderize.backoffice_api.service;
import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaRequestDto;
import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.pizza.OrderPizzaRequestToOrderPizza;
import com.orderize.backoffice_api.mapper.order.pizza.OrderPizzaToOrderPizzaResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.OrderPizza;
import com.orderize.backoffice_api.repository.OrderPizzaRepository;
import com.orderize.backoffice_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderPizzaService {
    private final OrderPizzaRepository repository;
    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;

    private final OrderPizzaToOrderPizzaResponse mapperResponse;
    private final OrderPizzaRequestToOrderPizza mapperRequest;

    public OrderPizzaService(
            OrderPizzaRepository repository,
            OrderRepository orderRepository,
            PizzaRepository pizzaRepository,
            OrderPizzaToOrderPizzaResponse mapperResponse,
            OrderPizzaRequestToOrderPizza mapperRequest){
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
        this.mapperResponse = mapperResponse;
        this.mapperRequest = mapperRequest;
    }

    public OrderPizzaResponseDto saveOrderPizza(Long orderId, Long pizzaId, OrderPizzaRequestDto requestDto){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada"));

        OrderPizza orderPizzaToSave = mapperRequest.map(requestDto, order, pizza);
        repository.save(orderPizzaToSave);
        return mapperResponse.map(orderPizzaToSave);
    }

    public void deleteOrderPizza(Long orderId, Long pizzaId){
        OrderPizza.OrderPizzaId id = new OrderPizza.OrderPizzaId(orderId, pizzaId);
        OrderPizza orderPizza = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderPizza não encontrada para os IDs fornecidos"));

        repository.deleteById(orderPizza.getId());
    }

}
