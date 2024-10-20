package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.order.drink.OrderDrinkRequestDto;
import com.orderize.backoffice_api.dto.order.drink.OrderDrinkResponseDto;
import com.orderize.backoffice_api.service.OrderDrinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderId}/drinks", produces = {"application/json"})
@Tag(name = "/orders/drinks")
public class OrderDrinkController {
    private final OrderDrinkService service;

    public OrderDrinkController(OrderDrinkService service){
        this.service = service;
    }

    @PostMapping("/{drinkId")
    @Operation(summary = "Salva uma nova bebida em um pedido", method = "POST")
    public ResponseEntity<OrderDrinkResponseDto> saveOrderDrink(
            @RequestBody @Valid OrderDrinkRequestDto request,
            @PathVariable Long orderId,
            @PathVariable Long drinkId
    ){
        OrderDrinkResponseDto orderDrink = service.saveOrderDrink(orderId, drinkId, request);
        return ResponseEntity.status(201).body(orderDrink);
    }

    @DeleteMapping("/{drinkId}")
    @Operation(summary = "Deleta uma bebida de um pedido", method = "DELETE")
    public ResponseEntity<Void> deleteOrderDrink(
            @PathVariable Long orderId,
            @PathVariable Long drinkId
    ){
        service.deleteOrderDrink(orderId, drinkId);
        return ResponseEntity.status(204).build();
    }
}
