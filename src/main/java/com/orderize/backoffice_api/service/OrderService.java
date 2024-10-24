package com.orderize.backoffice_api.service.order;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.drink.DrinkRepository;
import com.orderize.backoffice_api.repository.order.OrderRepository;
import com.orderize.backoffice_api.repository.pizza.PizzaRepository;
import com.orderize.backoffice_api.repository.user.UserRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderToOrderResponse mapperOrderToOrderResponse;
    private final OrderRequestToOrder mapperOrderRequestToOrder;

    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    private final DrinkRepository drinkRepository;

    public OrderService(
            OrderRepository repository,
            OrderToOrderResponse mapperOrderToOrderResponse,
            OrderRequestToOrder mapperOrderRequestToOrder,
            UserRepository userRepository,
            PizzaRepository pizzaRepository,
            DrinkRepository drinkRepository
    ){
        this.repository = repository;
        this.mapperOrderToOrderResponse = mapperOrderToOrderResponse;
        this.mapperOrderRequestToOrder = mapperOrderRequestToOrder;
        this.userRepository = userRepository;
        this.pizzaRepository = pizzaRepository;
        this.drinkRepository = drinkRepository;
    }

    public OrderResponseDto getOrderById(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        return mapperOrderToOrderResponse.map(order);
    }

    public List<OrderResponseDto> getAllOrders(String type){
        List<Order> orders = repository.findAll();
        if (type != null && !type.isBlank()){
            orders = orders.stream().filter(it -> it.getType().toLowerCase().contains(type.toLowerCase())).toList();
        }
        return orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList();
    }

    public OrderResponseDto saveOrder(OrderRequestDto orderResquestDto){
        User client = userRepository.findById(orderResquestDto.client())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        User responsible = userRepository.findById(orderResquestDto.responsible())
            .orElseThrow(() -> new ResourceNotFoundException("Resposável não encontrado"));


        // List<Pizza> pizzas = repository.findPizzasById(orderResquestDto);
        List<Pizza> pizzas = pizzaRepository.findAllById(orderResquestDto.pizzas());
        if (pizzas.isEmpty()) throw new ResourceNotFoundException("Pizzas não encontradas");
        List<Drink> drinks = drinkRepository.findAllById(orderResquestDto.drinks()); 
        if (drinks.isEmpty()) throw new ResourceNotFoundException("Drinks não encontrados");

        /// REFATORAR PARA QUE AS SERVICES RETORNEM OBJETO ENTITY AO INVÉS DE DTO, PARA SEREM

        Order orderToSave = mapperOrderRequestToOrder.map(orderResquestDto, client, responsible, pizzas, drinks);
        Order savedOrder = repository.save(orderToSave);
        
        calculateOrderPrices(savedOrder);

        return mapperOrderToOrderResponse.map(orderToSave);
    }

    // Tem outra forma de fazer isso que é fazendo um select nas tabelas de relacionamento atraves do id do usuario
    OrderResponseDto calculateOrderPrices(Order order){
        BigDecimal orderValue = BigDecimal.ZERO;

        orderValue = orderValue.add(order.getPizzas().stream()
                        .map(pizza -> pizza.getFlavors().stream()
                            .map(Flavor::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                                    .reduce(BigDecimal.ZERO, BigDecimal::add));

        orderValue = orderValue.add(order.getDrinks().stream()
                        .map(Drink::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));

        return mapperOrderToOrderResponse.map(repository.save(order));
    }

    public OrderResponseDto updateOrder(OrderRequestDto orderRequestDto, Long id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Pedido não encontrado");

        User client = userRepository.findById(orderRequestDto.client())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente (user) não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
            .orElseThrow(() -> new ResourceNotFoundException("Responsável (user) não encontrado"));

        List<Pizza> pizzas = pizzaRepository.findAllById(orderRequestDto.pizzas());
        if (pizzas.isEmpty()) throw new ResourceNotFoundException("Pizzas não encontradas");

        List<Drink> drinks = drinkRepository.findAllById(orderRequestDto.drinks());
        if (drinks.isEmpty()) throw new ResourceNotFoundException("Drinks não encontrados");

        Order orderToUpdate = mapperOrderRequestToOrder.map(orderRequestDto, client, responsible, pizzas, drinks);

        return mapperOrderToOrderResponse.map(repository.save(orderToUpdate));
    }

    public void deleteOrder(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        repository.deleteById(order.getId());
    }
}
