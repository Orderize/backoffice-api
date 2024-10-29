package com.orderize.backoffice_api.model;

import java.math.BigDecimal;
import java.util.List;

import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Pizza {
    
    // @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Integer id;

    // private String name;

    // private Double price;

    // private Double estimatedTimeFinishing;

    // private String image;

    // @ManyToMany
    // private List<Ingredient> ingredients;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "pizza_flavor", 
        joinColumns = @JoinColumn(name = "pizza_id"), 
        inverseJoinColumns = @JoinColumn(name = "flavor_id")
    )
    private List<Flavor> flavors;

    private String name;
    private BigDecimal price;
    private String observations;

    public Pizza() {
    }

    public Pizza(Long id, String name, BigDecimal price, String observations) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.observations = observations;
    }

    public Pizza(String name, BigDecimal price, String observations, List<Flavor> flavors) {
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
    }


    public Pizza(Long id, String name, BigDecimal price, String observations, List<Flavor> flavors) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
    }

    public Long getIdPizza() {
        return id;
    }

    public void setIdPizza(Long id) {
        this.id = id;
    }

    public List<Flavor> getFlavor() {
        return flavors;
    }

    public void setFlavor(List<Flavor> flavors) {
        this.flavors = flavors;
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
