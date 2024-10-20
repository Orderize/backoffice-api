package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_pizza")
public class OrderPizza {
    @EmbeddedId
    private OrderPizzaId id;

    @ManyToOne
    @MapsId("fk_order")
    @JoinColumn(name = "fk_order")
    private Order order;

    @ManyToOne
    @MapsId("fk_pizza")
    @JoinColumn(name = "fk_pizza")
    private Pizza pizza;

    private Integer quantity;
    private Double grossPrice;
    private Double netPrice;

    public OrderPizza() {
    }

    public OrderPizza(OrderPizzaId id, Order order, Pizza pizza, Integer quantity, Double grossPrice, Double netPrice) {
        this.id = id;
        this.order = order;
        this.pizza = pizza;
        this.quantity = quantity;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
    }

    public OrderPizzaId getId() {
        return id;
    }

    public void setId(OrderPizzaId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(Double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }

    @Embeddable
    public record OrderPizzaId(
            @Column(name = "fk_order")
            Long fkOrder,
            @Column(name = "fk_pizza")
            Long fkPizza
    ){ }
}
