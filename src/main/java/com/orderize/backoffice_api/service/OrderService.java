package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.enumeration.OrderStatusEnumeration;
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
import com.orderize.backoffice_api.util.observer.order_attestation.OrderObserverSubject;

@Service
public class OrderService implements OrderObserverSubject {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderToOrderResponse mapperOrderToOrderResponse;
    @Autowired
    private OrderRequestToOrder mapperOrderRequestToOrder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private DrinkRepository drinkRepository;

    private final List<OrderObserver> observers = new ArrayList<>();

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

    public List<OrderResponseDto> getLastOrders(Instant datetime) {
        List<Order> orders;
        if (datetime != null) {
            orders = repository.findByDatetimeBeforeOrderByDatetimeAsc(datetime);
        } else {
            orders = repository.findAllByOrderByDatetimeAsc();
        }

        return orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList();
    }

    public List<Order> getPendingStatusOrders() {
        return repository.findByStatus(OrderStatusEnumeration.PENDENTE.getValue());
    }

    public List<Order> getPreparationStatusOrders() {
        return repository.findByStatus(OrderStatusEnumeration.EM_PREPARO.getValue());
    }

    public List<Order> getAvailableStatusOrders() {
        return repository.findByStatus(OrderStatusEnumeration.DISPONIVEL.getValue());
    }

    public Order updateStatusToPendingOrders(Long id) {
        Order orderToUpdate = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order não encontrado"));
        
        orderToUpdate.setStatus(OrderStatusEnumeration.PENDENTE.getValue());
        
        return repository.save(orderToUpdate);
    }

    public Order updateStatusToPreparationOrders(Long id) {
        Order orderToUpdate = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order não encontrado"));
        
        orderToUpdate.setStatus(OrderStatusEnumeration.EM_PREPARO.getValue());
        
        return repository.save(orderToUpdate);
    }

    public Order updateStatusToAvailableOrders(Long id) {
        Order orderToUpdate = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order não encontrado"));
        
        orderToUpdate.setStatus(OrderStatusEnumeration.DISPONIVEL.getValue());

        return repository.save(orderToUpdate);
    }

    public List<Order> getOrdersFromToday() {
        ZoneId zoneId = ZoneId.systemDefault(); 

        LocalDate today = LocalDate.now(zoneId);
        Instant startOfDay = today.atStartOfDay(zoneId).toInstant();
        Instant endOfDay = today.atTime(LocalTime.MAX).atZone(zoneId).toInstant();


        return repository.findByLastModifiedBetween(startOfDay, endOfDay);
    }

    public OrderResponseDto saveOrder(OrderRequestDto orderRequestDto){
        User client = userRepository.findById(orderRequestDto.client())
            .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
            .orElseThrow(() -> new ResourceNotFoundException("Resposável não encontrado"));


        List<Pizza> pizzas = orderRequestDto.pizzas() != null ? pizzaRepository.findAllById(orderRequestDto.pizzas()) : new ArrayList<>();
        List<Drink> drinks = orderRequestDto.drinks() != null ? drinkRepository.findAllById(orderRequestDto.drinks()) : new ArrayList<>(); 

        Order orderToSave = mapperOrderRequestToOrder.map(orderRequestDto);
        orderToSave.setClient(client);
        orderToSave.setResponsible(responsible);
        orderToSave.setPizzas(pizzas);
        orderToSave.setDrinks(drinks);

        calculateOrderPrices(orderToSave);

        Order savedOrder = repository.save(orderToSave);

        notifyObservers(savedOrder);

        savedOrder = repository.save(savedOrder);

        return mapperOrderToOrderResponse.map(savedOrder);
    }

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
    
        Order orderToUpdate = mapperOrderRequestToOrder.map(orderRequestDto);
        orderToUpdate.setClient(client);
        orderToUpdate.setResponsible(responsible);
        orderToUpdate.setPizzas(pizzas);
        orderToUpdate.setDrinks(drinks);

        calculateOrderPrices(orderToUpdate);
        orderToUpdate.setId(id);

        return mapperOrderToOrderResponse.map(repository.save(orderToUpdate));
    }

    public void deleteOrder(Long id){
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        repository.deleteById(order.getId());
    }

    public BigDecimal getTotalPrice(OrderRequestDto orderRequestDto) {
        User client = userRepository.findById(orderRequestDto.client())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        User responsible = userRepository.findById(orderRequestDto.responsible())
                .orElseThrow(() -> new ResourceNotFoundException("Resposável não encontrado"));

        List<Pizza> pizzas = new ArrayList<>();
        if(orderRequestDto.pizzas() != null) pizzas = pizzaRepository.findAllById(orderRequestDto.pizzas());
        List<Drink> drinks = new ArrayList<>();
        if (orderRequestDto.drinks() != null) drinks = drinkRepository.findAllById(orderRequestDto.drinks());

        Order orderToSave = mapperOrderRequestToOrder.map(orderRequestDto);
        orderToSave.setClient(client); 
        orderToSave.setResponsible(responsible); 
        orderToSave.setPizzas(pizzas); 
        orderToSave.setDrinks(drinks);

        calculateOrderPrices(orderToSave);
        return orderToSave.getPrice();
    }
}
