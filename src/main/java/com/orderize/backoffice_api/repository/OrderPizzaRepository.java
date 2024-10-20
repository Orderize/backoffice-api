package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.model.OrderPizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPizzaRepository extends JpaRepository<OrderPizza, OrderPizza.OrderPizzaId> {
    List<OrderPizza> findByOrderId(Long orderId);
}
