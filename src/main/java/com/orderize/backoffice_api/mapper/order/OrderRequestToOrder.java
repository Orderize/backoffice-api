package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class OrderRequestToOrder {

    public Order map(OrderRequestDto orderRequestDto, User client, User responsible) {
        return new Order(
                null,
                client,
                responsible,
                orderRequestDto.type(),
                orderRequestDto.freight(),
                orderRequestDto.estimativeTime()
        );
    }
}
