package com.orderize.backoffice_api.mapper.order.drink;

import com.orderize.backoffice_api.dto.order.drink.OrderDrinkResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.OrderDrink;
import org.springframework.stereotype.Component;

@Component
public class OrderDrinkToOrderDrinkResponse implements Mapper<OrderDrink, OrderDrinkResponseDto> {

    @Override
    public OrderDrinkResponseDto map(OrderDrink orderDrink){
        return new OrderDrinkResponseDto(
                orderDrink.getId().fkOrder(),
                orderDrink.getId().fkDrink(),
                orderDrink.getQuantity(),
                orderDrink.getGrossPrice(),
                orderDrink.getNetPrice()
        );
    }
}
