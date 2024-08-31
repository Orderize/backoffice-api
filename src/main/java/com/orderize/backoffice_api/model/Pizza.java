package com.orderize.backoffice_api.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Pizza {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double price;

    private Double estimatedTimeFinishing;

    private String image;

    @ManyToMany
    private List<Ingredient> ingredients;

    public Pizza() {
    }

    public Pizza(String name, Double price, Double estimatedTimeFinishing, String image, List<Ingredient> ingredients) {
        this.name = name;
        this.price = price;
        this.estimatedTimeFinishing = estimatedTimeFinishing;
        this.image = image;
        this.ingredients = ingredients;
    }

    public Pizza(Integer id, String name, Double price, Double estimatedTimeFinishing, String image,
            List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.estimatedTimeFinishing = estimatedTimeFinishing;
        this.image = image;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getEstimatedTimeFinishing() {
        return estimatedTimeFinishing;
    }

    public void setEstimatedTimeFinishing(Double estimatedTimeFinishing) {
        this.estimatedTimeFinishing = estimatedTimeFinishing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
