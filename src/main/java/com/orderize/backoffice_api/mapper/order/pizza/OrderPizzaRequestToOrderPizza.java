package com.orderize.backoffice_api.mapper.order.pizza;

import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaRequestDto;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.OrderPizza;
import org.springframework.stereotype.Component;

@Component
public class OrderPizzaRequestToOrderPizza {

    public OrderPizza map(OrderPizzaRequestDto request, Order order, Pizza pizza){
        OrderPizza.OrderPizzaId id = new OrderPizza.OrderPizzaId(order.getId(), pizza.getIdPizza());
        return new OrderPizza(
                id,
                order,
                pizza,
                request.quantity(),
                request.grossPrice(),
                request.netPrice()
        );
    }

}
