package com.orderize.backoffice_api.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "pizza_flavor", 
        joinColumns = @JoinColumn(name = "pizza_id"), 
        inverseJoinColumns = @JoinColumn(name = "flavor_id")
    )
    private List<Flavor> flavors;


    @ManyToMany(
        mappedBy="pizzas"
    )
    private List<Order> orders;

    private String name;
    private BigDecimal price;
    private String observations;
    private String border;
    private String size;
    private String mass;


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

    public Pizza(Long id, String name, BigDecimal price, String observations, List<Flavor> flavors, String border, String size, String mass) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
        this.border = border;
        this.size = size;
        this.mass = mass;
    }


    public Pizza(Long id, String name, BigDecimal price, String observations, List<Flavor> flavors, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
        this.orders = orders;
    }

    public Pizza(String name, BigDecimal price, String observations, List<Flavor> flavors, String border, String size, String mass) {
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
        this.border = border;
        this.size = size;
        this.mass = mass;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

}
