package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_drink")
public class OrderDrink {
    @EmbeddedId
    private OrderDrinkId id;

    @ManyToOne
    @MapsId("fk_order")
    @JoinColumn(name = "fk_order")
    private Order order;

    @ManyToOne
    @MapsId("fk_drink")
    @JoinColumn(name = "fk_drink")
    private Drink drink;

    private Integer quantity;
    private Double grossPrice;
    private Double netPrice;

    public OrderDrink() {
    }

    public OrderDrink(OrderDrinkId id, Order order, Drink drink, Integer quantity, Double grossPrice, Double netPrice) {
        this.id = id;
        this.order = order;
        this.drink = drink;
        this.quantity = quantity;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
    }

    public OrderDrinkId getId() {
        return id;
    }

    public void setId(OrderDrinkId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
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
    public record OrderDrinkId(
            @Column(name = "fk_order")
            Long fkOrder,
            @Column(name = "fk_drink")
            Long fkDrink
    ){}

}
