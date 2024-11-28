package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.order.OrderRequestToOrder;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.dto.order.OrderRequestDto;

import com.orderize.backoffice_api.repository.DrinkRepository;
import com.orderize.backoffice_api.repository.OrderRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;
import com.orderize.backoffice_api.repository.UserRepository;
import com.orderize.backoffice_api.util.observer.order_attestation.OrderObserver;
import com.orderize.backoffice_api.util.observer.order_attestation.OrderObserverSubject;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// TODO: Refatorar
@Service
public class OrderService implements OrderObserverSubject {
    private final OrderRepository repository;
    private final OrderToOrderResponse mapperOrderToOrderResponse;
    private final OrderRequestToOrder mapperOrderRequestToOrder;

    private final UserRepository userRepository;
    private final PizzaRepository pizzaRepository;
    private final DrinkRepository drinkRepository;
    private final List<OrderObserver> observers;

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
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Order order) {
        for (OrderObserver observer : observers) {
            observer.onOrderCreated(order);
        }
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

    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto){
        User client = userRepository.findById(orderRequestDto.client())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
            .orElseThrow(() -> new ResourceNotFoundException("Resposável não encontrado"));


        // List<Pizza> pizzas = repository.findPizzasById(orderRequestDto);
        List<Pizza> pizzas = new ArrayList<>();
        if(orderRequestDto.pizzas() != null) pizzas = pizzaRepository.findAllById(orderRequestDto.pizzas());
        List<Drink> drinks = new ArrayList<>();
        if (orderRequestDto.drinks() != null) drinks = drinkRepository.findAllById(orderRequestDto.drinks()); 

        /// REFATORAR PARA QUE AS SERVICES RETORNEM OBJETO ENTITY AO INVÉS DE DTO, PARA SEREM

        Order orderToSave = mapperOrderRequestToOrder.map(orderRequestDto, client, responsible, pizzas, drinks);
        
        calculateOrderPrices(orderToSave);

        Order savedOrder = repository.save(orderToSave);

        notifyObservers(savedOrder);

        /*
        Estou salvando a order duas vezes porque ela precisa ser salva antes de notificar os observers para ter o id
        gerado (caso do Attestation), mas ela também pode ser alterada por algum observer como será o de promoções
        então ela é salva e atualizada dps
         */
        return mapperOrderToOrderResponse.map(repository.save(savedOrder));
    }

    // Tem outra forma de fazer isso que é fazendo um select nas tabelas de relacionamento atraves do id do usuario
    void calculateOrderPrices(Order order){
        BigDecimal orderValue = BigDecimal.ZERO;

        if (order.getPizzas() != null) {
            for (int i = 0; i < order.getPizzas().size(); i++) {
                orderValue = orderValue.add(order.getPizzas().get(i).getPrice());
            }
        }

        if (order.getDrinks() != null) {
            orderValue = orderValue.add(order.getDrinks().stream()
                            .map(Drink::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        if (order.getFreight() != null) {
            orderValue = orderValue.add(order.getFreight());
        }
        order.setPrice(orderValue);
    }

    public OrderResponseDto updateOrder(OrderRequestDto orderRequestDto, Long id){
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Pedido não encontrado");

        User client = userRepository.findById(orderRequestDto.client())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente (user) não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
            .orElseThrow(() -> new ResourceNotFoundException("Responsável (user) não encontrado"));

        List<Pizza> pizzas = new ArrayList<>();
        if(orderRequestDto.pizzas() != null) pizzas = pizzaRepository.findAllById(orderRequestDto.pizzas());
        List<Drink> drinks = new ArrayList<>();
        if (orderRequestDto.drinks() != null) drinks = drinkRepository.findAllById(orderRequestDto.drinks()); 
    
        Order orderToUpdate = mapperOrderRequestToOrder.map(orderRequestDto, client, responsible, pizzas, drinks);

        calculateOrderPrices(orderToUpdate);
        orderToUpdate.setId(id);

        return mapperOrderToOrderResponse.map(repository.save(orderToUpdate));
    }

    public void deleteOrder(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        repository.deleteById(order.getId());
    }

    // TODO: horrível porém peguei da saveOrder porque a necessidade era urgente, refatorar
    // TODO: Criar testes unitários, não criei ainda pq a service será refatorada
    public BigDecimal getTotalPrice(OrderRequestDto orderRequestDto) {
        User client = userRepository.findById(orderRequestDto.client())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
                .orElseThrow(() -> new ResourceNotFoundException("Resposável não encontrado"));

        List<Pizza> pizzas = new ArrayList<>();
        if(orderRequestDto.pizzas() != null) pizzas = pizzaRepository.findAllById(orderRequestDto.pizzas());
        List<Drink> drinks = new ArrayList<>();
        if (orderRequestDto.drinks() != null) drinks = drinkRepository.findAllById(orderRequestDto.drinks());

        Order orderToSave = mapperOrderRequestToOrder.map(orderRequestDto, client, responsible, pizzas, drinks);

        calculateOrderPrices(orderToSave);
        return orderToSave.getPrice();
    }
}
