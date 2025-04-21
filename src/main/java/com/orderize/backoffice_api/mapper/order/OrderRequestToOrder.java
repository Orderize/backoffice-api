package com.orderize.backoffice_api.mapper.order;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Order;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class OrderRequestToOrder implements Mapper<OrderRequestDto, Order>{

    @Override
    public Order map(OrderRequestDto orderRequestDto) {
        return Order.builder()
            .type(orderRequestDto.type())
            .freight(orderRequestDto.freight())
            .estimatedTime(orderRequestDto.estimatedTime())
            .price(BigDecimal.valueOf(0.0))
            .status(orderRequestDto.status())
            .table(orderRequestDto.table())
            .build();
        }
}
