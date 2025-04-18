package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.dto.user.UserResponseDto;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

// TODO: Jogar isso fora e refatorar
@Component
public class OrderRequestToOrder {

    public Order map(OrderRequestDto orderRequestDto, User client, User responsible, List<Pizza> pizzas, List<Drink> drinks) {
        return new Order(
                client,
                responsible,
                pizzas,
                drinks,
                orderRequestDto.type(),
                orderRequestDto.freight(),
                orderRequestDto.estimatedTime(),
                BigDecimal.valueOf(0.0)
        );
    }
}
