package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaRequestDto;
import com.orderize.backoffice_api.dto.order.pizza.OrderPizzaResponseDto;
import com.orderize.backoffice_api.service.OrderPizzaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderId}/pizzas", produces = {"application/json"})
@Tag(name = "/orders/pizzas")
public class OrderPizzaController {

    private final OrderPizzaService service;

    public OrderPizzaController(OrderPizzaService service){
        this.service = service;
    }

    @PostMapping("/{pizzaId}")
    @Operation(summary = "Salva uma nova pizza em um pedido", method = "POST")
    public ResponseEntity<OrderPizzaResponseDto> saveOrderPizza(
            @RequestBody @Valid OrderPizzaRequestDto request,
            @PathVariable Long orderId,
            @PathVariable Long pizzaId
    ){
        OrderPizzaResponseDto orderPizza = service.saveOrderPizza(orderId, pizzaId, request);
        return ResponseEntity.status(201).body(orderPizza);
    }

    @DeleteMapping("/{pizzaId}")
    @Operation(summary = "Deleta uma pizza de um pedido", method = "DELETE")
    public ResponseEntity<Void> deleteOrderPizza(
            @PathVariable Long orderId,
            @PathVariable Long pizzaId
    ){
        service.deleteOrderPizza(orderId, pizzaId);
        return ResponseEntity.status(204).build();
    }
}
