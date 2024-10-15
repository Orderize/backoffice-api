package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/pedidos", produces = {"application/json"})
@Tag(name = "/pedidos")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pedido por id", method = "GET")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable("id") Long id
    ) {
        OrderResponseDto order = service.getOrderById(id);

        if (order != null){
            return ResponseEntity.status(200).body(order);
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping
    @Operation(
            summary = "Busca todos os pedidos",
            method = "GET",
            description = "Pode receber o request param opcional [type]"+
            "filtrando o resultado com base no request param passado,"+
            "e caso nenhum seja passado retorna uma list com todos os pedidos.")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(
            @RequestParam String type
    ){
        List<OrderResponseDto> orders = service.getAllOrders(type);
        if (orders.isEmpty()){
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(orders);
        }
    }

    @PostMapping("/{id}")
    @Operation(summary = "Salva um novo usuário", method = "POST")
    public ResponseEntity<OrderResponseDto> saveOrder(
            @RequestBody @Valid OrderRequestDto orderResquest
    ){
        OrderResponseDto order = service.saveOrder(orderResquest);
        return ResponseEntity.status(201).body(order);
    }

    @PutMapping
    @Operation(summary = "Atualiza um pedido", method = "PUT")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @RequestBody OrderRequestDto orderToUpdate
    ){
        OrderResponseDto order = service.updateOrder(orderToUpdate);
        return ResponseEntity.status(200).body(order);
    }

    @DeleteMapping
    @Operation(summary = "Deleta um pedido", method = "DELETE")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable("id") Long id
    ){
        service.deleteOrder(id);
        return ResponseEntity.status(204).build();
    }
}
