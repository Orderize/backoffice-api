package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Order;
import org.springframework.stereotype.Component;


@Component
public class OrderToOrderResponse implements Mapper<Order, OrderResponseDto> {

    @Override
    public OrderResponseDto map(Order order){

        return new OrderResponseDto(
                order.getId(),
                order.getClient().getId(),
                order.getResponsible().getId(),
                order.getDatetime_order(),
                order.getType(),
                order.getFreight(),
                order.getEstimativeTime(),
                order.getPrice());
    }
}
