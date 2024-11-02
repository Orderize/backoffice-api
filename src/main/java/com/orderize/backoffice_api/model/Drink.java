package com.orderize.backoffice_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Drink {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    BigDecimal price;
    Integer milimeters;

    public Drink() {}

    public Drink(Long id, String name, String description, BigDecimal price, Integer milimeters) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.milimeters = milimeters;
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

    public Integer getMilimeters() {
        return milimeters;
    }

    public void setMilimeters(Integer milimeters) {
        this.milimeters = milimeters;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", milimeters=" + milimeters +
                '}';
    }
}
