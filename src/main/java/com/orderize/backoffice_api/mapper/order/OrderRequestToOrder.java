package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderRequestToOrder {

    public Order map(OrderRequestDto orderRequestDto, User client, User responsible, List<Pizza> pizzas, List<Drink> drinks) {
        return new Order(
            client,
            responsible,
            pizzas,
            drinks,
            orderRequestDto.datetime_order(),
            orderRequestDto.type(),
            orderRequestDto.freight(),
            orderRequestDto.estimativeTime(),
            orderRequestDto.price()
        );
    }
}
