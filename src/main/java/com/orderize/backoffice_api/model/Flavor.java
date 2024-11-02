package com.orderize.backoffice_api.model;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Flavor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDate registered;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "flavor_ingredient",
        joinColumns = @JoinColumn(name = "flavor_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToMany(
        mappedBy="flavors"
    )
    private List<Pizza> pizzas;

    public Flavor() {
    }

    public Flavor(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Flavor(String name, String description, BigDecimal price, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
    }

    public Flavor(Long id, String name, String description, BigDecimal price, LocalDate registered, List<Ingredient> ingredients, List<Pizza> pizzas) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.registered = registered;
        this.ingredients = ingredients;
        this.pizzas = pizzas;
    }

    public Flavor(String name, String description, BigDecimal price, LocalDate registered, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.registered = registered;
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

}
