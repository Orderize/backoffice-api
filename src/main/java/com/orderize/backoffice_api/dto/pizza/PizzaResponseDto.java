package com.orderize.backoffice_api.dto.pizza;

import java.math.BigDecimal;
import java.util.List;

import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;


public class PizzaResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String observations;
    private List<FlavorResponseDto> flavors;

    public PizzaResponseDto(Long id, String name, BigDecimal price, String observations, List<FlavorResponseDto> flavors) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
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

    public List<FlavorResponseDto> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<FlavorResponseDto> flavors) {
        this.flavors = flavors;
    }
}
