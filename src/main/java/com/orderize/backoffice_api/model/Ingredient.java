package com.orderize.backoffice_api.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal paid;
    @ManyToMany(mappedBy="ingredients")
    private List<Flavor> flavors;
        
    public Ingredient() {
    }
    
    public Ingredient(String name, BigDecimal paid) {
        this.name = name;
        this.paid = paid;
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
    
    public BigDecimal getPaid() {
        return paid;
    }
    
    public void setPaid(BigDecimal paid) {
        this.paid = paid;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }
    
}
