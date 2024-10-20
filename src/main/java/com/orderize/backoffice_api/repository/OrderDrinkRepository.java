package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.OrderDrink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDrinkRepository extends JpaRepository<OrderDrink, OrderDrink.OrderDrinkId> {
    List<OrderDrink> findByOrderId(Long orderId);
}
