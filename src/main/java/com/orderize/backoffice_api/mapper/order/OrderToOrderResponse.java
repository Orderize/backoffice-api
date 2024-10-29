package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponseDto;
import com.orderize.backoffice_api.mapper.user.UserToUserResponseDto;
import com.orderize.backoffice_api.model.Order;

import org.springframework.stereotype.Component;


@Component
public class OrderToOrderResponse {

    private final UserToUserResponseDto mapperUserToUserResponse;
    private final PizzaToPizzaResponseDto mapperPizzaToPizzaResponse;
    private final DrinkToDrinkResponse mapperDrinkToDrinkResponse;

    public OrderToOrderResponse(UserToUserResponseDto mapperUserToUserResponse, PizzaToPizzaResponseDto mapperPizzaToPizzaResponse, DrinkToDrinkResponse mapperDrinkToDrinkResponse) {
        this.mapperUserToUserResponse = mapperUserToUserResponse;
        this.mapperPizzaToPizzaResponse = mapperPizzaToPizzaResponse;
        this.mapperDrinkToDrinkResponse = mapperDrinkToDrinkResponse;
    }

    public OrderResponseDto map(Order order){
        return new OrderResponseDto(
            order.getId(),
            mapperUserToUserResponse.map(order.getClient()),
            mapperUserToUserResponse.map(order.getResponsible()),
            order.getPizzas().stream().map(mapperPizzaToPizzaResponse::map).toList(), 
            order.getDrinks().stream().map(mapperDrinkToDrinkResponse::map).toList(),
            order.getDatetime(),
            order.getType(),
            order.getFreight(),
            order.getEstimatedTime(),
            order.getPrice()
        );
    }
}
