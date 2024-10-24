package com.orderize.backoffice_api.service.order;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.OrderDrink;
import com.orderize.backoffice_api.model.OrderPizza;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.drink.DrinkRepository;
import com.orderize.backoffice_api.repository.order.OrderDrinkRepository;
import com.orderize.backoffice_api.repository.order.OrderPizzaRepository;
import com.orderize.backoffice_api.repository.order.OrderRepository;
import com.orderize.backoffice_api.repository.pizza.PizzaRepository;
import com.orderize.backoffice_api.repository.user.UserRepository;
import com.orderize.backoffice_api.service.drink.DrinkService;
import com.orderize.backoffice_api.service.pizza.PizzaService;
import com.orderize.backoffice_api.service.user.UserService;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final OrderToOrderResponse mapperOrderToOrderResponse;
    private final OrderRequestToOrder mapperOrderRequestToOrder;

    private final UserService userService;
    private final PizzaService pizzaService;
    private final DrinkService drinkService;

    public OrderService(
            OrderRepository repository,
            OrderToOrderResponse mapperOrderToOrderResponse,
            OrderRequestToOrder mapperOrderRequestToOrder,
            UserService userService,
            PizzaService pizzaService,
            DrinkService drinkService
    ){
        this.repository = repository;
        this.mapperOrderToOrderResponse = mapperOrderToOrderResponse;
        this.mapperOrderRequestToOrder = mapperOrderRequestToOrder;
        this.userService = userService;
        this.pizzaService = pizzaService;
        this.drinkService = drinkService;
    }

    public OrderResponseDto calculateOrderPrices(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        Double grossTotal = 0.;
        Double netTotal = 0.;

        // tenho que pegar todas as pizzas com os ids/ pega o valor de todas as pizzas que retornar
        Optional<Pizza> pizzas = pizzas.findById(id);

        for (OrderPizza pizza : pizzas){
            grossTotal += pizza.getGrossPrice() * pizza.getQuantity();
            netTotal += pizza.getNetPrice() * pizza.getQuantity();
        }

        List<OrderDrink> drinks = orderDrinkRepository.findByOrderId(id);
        for (OrderDrink drink : drinks){
            grossTotal += drink.getGrossPrice() * drink.getQuantity();
            netTotal += drink.getNetPrice() * drink.getQuantity();
        }

        netTotal += order.getFreight();
        order.setGrossPrice(grossTotal);
        order.setNetPrice(netTotal);

        return mapperOrderToOrderResponse.map(repository.save(order));
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

    public OrderResponseDto saveOrder(OrderRequestDto orderResquest){
        UserResponseDto client = userService.getUserById(orderResquest.client());
        UserResponseDto responsible = userService.getUserById(orderResquest.responsible());

        // List<Pizza> pizzas = repository.findPizzasById(orderResquest);
        List<PizzaResponseDto> pizzas = pizzaService.getAllPizzas(orderResquest.pizzas());
        List<DrinkResponseDto> drinks = drinkService.getAllDrinks(orderResquest.drinks()); 


        /// REFATORAR PARA QUE AS SERVICES RETORNEM OBJETO ENTITY AO INVÉS DE DTO, PARA SEREM REUTILIZADAS AS SERVICES

        Order orderToSave = mapperOrderRequestToOrder.map(orderResquest, client, responsible);
        Order savedOrder = repository.save(orderToSave);

        calculateOrderPrices(savedOrder.getId());

        return mapperOrderToOrderResponse.map(orderToSave);
    }

    public OrderResponseDto updateOrder(OrderRequestDto orderToUpdate){
        Order order = repository.findById(orderToUpdate.id())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        User client = null;
        User responsible = null;

        if (orderToUpdate.client() != null){
            client = userRepository.findById(orderToUpdate.client())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente (user) não encontrado"));
        }
        if (orderToUpdate.responsible() != null){
            responsible = userRepository.findById(orderToUpdate.responsible())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsável (user) não encontrado"));
        }

        Order savingOrder = mapperOrderRequestToOrder.map(orderToUpdate, client, responsible);
        savingOrder.setId(order.getId());
        return mapperOrderToOrderResponse.map(repository.save(savingOrder));
    }

    public void deleteOrder(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        repository.deleteById(order.getId());
    }
}
