package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponseDto;
import com.orderize.backoffice_api.model.Order;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


@Component
public class OrderToOrderResponse implements Mapper<Order, OrderResponseDto> {

    private PizzaToPizzaResponseDto pizzaToPizzaResponseDto;
    private DrinkToDrinkResponse drinkToDrinkResponse;

    public OrderToOrderResponse(PizzaToPizzaResponseDto pizzaToPizzaResponseDto, DrinkToDrinkResponse drinkToDrinkResponse) {
        this.pizzaToPizzaResponseDto = pizzaToPizzaResponseDto;
        this.drinkToDrinkResponse = drinkToDrinkResponse;
    }

    @Override
    public OrderResponseDto map(Order order){

        return new OrderResponseDto(
                order.getId(),
                order.getClient(),
                order.getResponsible(),
                order.getPizzas().stream().map(it -> pizzaToPizzaResponseDto.map(it)).collect(Collectors.toList()),
                order.getDrinks().stream().map(it -> drinkToDrinkResponse.map(it)).collect(Collectors.toList()),
                order.getDatetime(),
                order.getType(),
                order.getFreight(),
                order.getEstimativeTime(),
                order.getPrice());
    }
}
