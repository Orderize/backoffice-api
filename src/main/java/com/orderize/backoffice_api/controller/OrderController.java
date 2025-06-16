package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.order.OrderRequestDto;
import com.orderize.backoffice_api.dto.order.OrderResponseDto;
import com.orderize.backoffice_api.dto.order.OrderTotalPriceResponseDto;
import com.orderize.backoffice_api.mapper.order.OrderToOrderResponse;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

// TODO: Refatorar
@RestController
@RequestMapping(value = "/orders", produces = {"application/json"})
@Tag(name = "/orders")
public class OrderController {

    @Autowired
    private OrderToOrderResponse mapperOrderToOrderResponse;
    
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
            @RequestParam(required = false) String type
    ){
        List<OrderResponseDto> orders = service.getAllOrders(type);
        if (orders.isEmpty()){
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(orders);
        }
    }

    @GetMapping("/last")
    @Operation(
        summary = "Busca todos os últimos pedidos",
        method = "GET",
        description = "Pode receber o request param opcional [date]" +
        "filtrando os dados de forma descrescente ou com base no parâmetro,"+
        "pelo atributo .datetime"
    )
    public ResponseEntity<List<OrderResponseDto>> getLastOrders(
        @RequestParam(required = false) Instant datetime
    )  {
        List<OrderResponseDto> orders = service.getLastOrders(datetime);
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(orders);
    }

    @GetMapping("/status/pending")
    @Operation(
        summary = "Busca todos os pedidos pendentes",
        method = "GET",
        description = "Retorna os pedidos que estão pendentes"
    )
    public ResponseEntity<List<OrderResponseDto>> getPendingStatusOrders() {
        List<Order> orders = service.getPendingStatusOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList());
    }
    
    @GetMapping("/status/preparation")
    @Operation(
        summary = "Busca todos os pedidos em preparação",
        method = "GET",
        description = "Retorna os pedidos que estão em preparo"
    )
    public ResponseEntity<List<OrderResponseDto>> getPreparationStatusOrders() {
        List<Order> orders = service.getPreparationStatusOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList());
    }
    
    @GetMapping("/status/available")
    @Operation(
        summary = "Busca todos os pedidos disponivel",
        method = "GET",
        description = "Retorna os pedidos que estão disponivel"
    )
    public ResponseEntity<List<OrderResponseDto>> getAvailableStatusOrders() {
        List<Order> orders = service.getAvailableStatusOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(orders.stream().map(it -> mapperOrderToOrderResponse.map(it)).toList());
    }

    @PostMapping
    @Operation(summary = "Salva um novo pedido", method = "POST")
    public ResponseEntity<OrderResponseDto> saveOrder(
            @RequestBody @Valid OrderRequestDto orderResquest
    ){
        OrderResponseDto order = service.saveOrder(orderResquest);
        return ResponseEntity.status(201).body(order);
    }

    @PutMapping
    @Operation(summary = "Atualiza um pedido", method = "PUT")
    public ResponseEntity<OrderResponseDto> updateOrder(
        @RequestBody OrderRequestDto orderToUpdate,
        @PathVariable Long id
    ){
        OrderResponseDto order = service.updateOrder(orderToUpdate, id);
        return ResponseEntity.status(200).body(order);
    }

    @PutMapping("/{id}/status/pending")
    @Operation(summary = "Atualiza pedido para status pendente", method = "PUT")
    public ResponseEntity<OrderResponseDto> updateToPending(@PathVariable Long id) {
        Order order = service.updateStatusToPendingOrders(id);
        return ResponseEntity.ok(mapperOrderToOrderResponse.map(order));
    }

    
    @PutMapping("/{id}/status/preparation")
    @Operation(summary = "Atualiza pedido para status em preparo", method = "PUT")
    public ResponseEntity<OrderResponseDto> updateToPreparation(@PathVariable Long id) {
        Order order = service.updateStatusToPreparationOrders(id);
        return ResponseEntity.ok(mapperOrderToOrderResponse.map(order));
    }

    
    @PutMapping("/{id}/status/available")
    @Operation(summary = "Atualiza pedido para status disponível", method = "PUT")
    public ResponseEntity<OrderResponseDto> updateToAvailable(@PathVariable Long id) {
        Order order = service.updateStatusToAvailableOrders(id);
        return ResponseEntity.ok(mapperOrderToOrderResponse.map(order));
    }

    
    @GetMapping("/today")
    @Operation(summary = "Busca pedidos do dia atual", method = "GET", description = "Retorna os pedidos criados no dia atual")
    public ResponseEntity<List<OrderResponseDto>> getTodayOrders() {
        List<Order> orders = service.getOrdersFromToday();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders.stream().map(mapperOrderToOrderResponse::map).toList());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um pedido", method = "DELETE")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable("id") Long id
    ){
        service.deleteOrder(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/total-price")
    @Operation(summary = "Retorna o valor total do pedido", method = "GET")
    public ResponseEntity<OrderTotalPriceResponseDto> getTotalPrice(
            @RequestBody @Valid OrderRequestDto orderResquest
    ) {
        return ResponseEntity.status(200).body(new OrderTotalPriceResponseDto(service.getTotalPrice(orderResquest)));
    }
}
