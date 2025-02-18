package com.orderize.backoffice_api.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderize.backoffice_api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByDatetimeAsc();

    List<Order> findByDatetimeBeforeOrderByDatetimeAsc(Instant datetime);
}
