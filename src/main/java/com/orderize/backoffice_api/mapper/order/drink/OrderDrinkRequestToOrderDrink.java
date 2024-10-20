package com.orderize.backoffice_api.mapper.order.drink;

import com.orderize.backoffice_api.dto.order.drink.OrderDrinkRequestDto;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.OrderDrink;
import org.springframework.stereotype.Component;

@Component
public class OrderDrinkRequestToOrderDrink {

    public OrderDrink map(OrderDrinkRequestDto request, Order order, Drink drink){
        OrderDrink.OrderDrinkId id = new OrderDrink.OrderDrinkId(order.getId(), drink.getId());
        return new OrderDrink(
                id,
                order,
                drink,
                request.quantity(),
                request.grossPrice(),
                request.netPrice()
        );
    }
}
