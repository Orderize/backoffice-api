package com.orderize.backoffice_api.model;

import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPizza;

    @ManyToOne
    @JoinColumn(name = "fk_flavor", nullable = false)
    private Flavor flavor;

    private String name;
    private BigDecimal price;
    private String observations;

    public Pizza() {
    }

    public Pizza(String name, BigDecimal price, String observations, Flavor flavor) {
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavor = flavor;
    }

    public Pizza(Long idPizza, String name, BigDecimal price, String observations, Flavor flavor) {
        this.idPizza = idPizza;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavor = flavor;
    }

    public Pizza(Long idPizza, String name, BigDecimal price, String observations, Long aLong) {
    }

    public Pizza(long l, String pepperoni, String deliciousPizza) {
    }

    public Pizza(long l, String pepperoni, BigDecimal bigDecimal, String deliciousPizza) {
    }

    public Long getIdPizza() {
        return idPizza;
    }

    public void setIdPizza(Long idPizza) {
        this.idPizza = idPizza;
    }

    public Flavor getFlavor() {
        return flavor;
    }

    public void setFlavor(Flavor flavor) {
        this.flavor = flavor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public PizzaResponseDto map(Pizza pizza) {
        return null;
    }
}
