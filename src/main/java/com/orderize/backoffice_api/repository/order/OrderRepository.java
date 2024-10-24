package com.orderize.backoffice_api.repository.order;

import com.orderize.backoffice_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
