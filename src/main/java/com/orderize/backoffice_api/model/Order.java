package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fk_client")
    private User client;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fk_responsible")
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

    @CurrentTimestamp
    private Timestamp datetime;

    private String type;
    private BigDecimal freight;

    @Column(name = "estimated_time")
    private Integer estimatedTime;
    private BigDecimal price;

    private String status;

    public Order() {
    }


    public Order(Long id, User client, User responsible, List<Pizza> pizzas, List<Drink> drinks, Timestamp datetime, String type, BigDecimal freight, Integer estimatedTime, BigDecimal price, String status) {
        this.id = id;
        this.client = client;
        this.responsible = responsible;
        this.pizzas = pizzas;
        this.drinks = drinks;
        this.datetime = datetime;
        this.type = type;
        this.freight = freight;
        this.estimatedTime = estimatedTime;
        this.price = price;
        this.status = status;
    }

    public Order(User client, User responsible, List<Pizza> pizzas, List<Drink> drinks, String type, BigDecimal freight, Integer estimatedTime, BigDecimal price, String status) {
        this.client = client;
        this.responsible = responsible;
        this.pizzas = pizzas;
        this.drinks = drinks;
        this.type = type;
        this.freight = freight;
        this.estimatedTime = estimatedTime;
        this.price = price;
        this.status = status;
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

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
