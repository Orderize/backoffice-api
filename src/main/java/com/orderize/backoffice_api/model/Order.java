package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private User client;

    @ManyToOne
    @JoinColumn(name = "responsible")
    private User responsible;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "order_pizza", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizzas;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "order_drink", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "drink_id")
    )
    private List<Drink> drinks;


    private Timestamp datetime;
    private String type;
    private BigDecimal freight;
    private Double estimativeTime;
    private BigDecimal price;

    public Order() {
    }


    public Order(Long id, User client, User responsible, List<Pizza> pizzas, List<Drink> drinks, Timestamp datetime, String type, BigDecimal freight, Double estimativeTime, BigDecimal price) {
        this.id = id;
        this.client = client;
        this.responsible = responsible;
        this.pizzas = pizzas;
        this.drinks = drinks;
        this.datetime = datetime;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
        this.price = price;
    }

    public Order(User client, User responsible, List<Pizza> pizzas, List<Drink> drinks, Timestamp datetime, String type, BigDecimal freight, Double estimativeTime, BigDecimal price) {
        this.client = client;
        this.responsible = responsible;
        this.pizzas = pizzas;
        this.drinks = drinks;
        this.datetime = datetime;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Double getEstimativeTime() {
        return estimativeTime;
    }

    public void setEstimativeTime(Double estimativeTime) {
        this.estimativeTime = estimativeTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
