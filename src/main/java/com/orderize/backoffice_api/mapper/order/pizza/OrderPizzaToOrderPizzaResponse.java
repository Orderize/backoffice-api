package com.orderize.backoffice_api.mapper.order.pizza;

import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.OrderPizza;
import org.springframework.stereotype.Component;

@Component
public class OrderPizzaToOrderPizzaResponse implements Mapper<OrderPizza, OrderPizzaResponseDto> {

    @Override
    public OrderPizzaResponseDto map(OrderPizza orderPizza){

        return new OrderPizzaResponseDto(
                orderPizza.getId().fkOrder(),
                orderPizza.getId().fkPizza(),
                orderPizza.getQuantity(),
                orderPizza.getGrossPrice(),
                orderPizza.getNetPrice()
        );
    }
}
