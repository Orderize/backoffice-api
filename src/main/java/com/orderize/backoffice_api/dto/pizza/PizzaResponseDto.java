package com.orderize.backoffice_api.dto.pizza;

import java.math.BigDecimal;
import java.util.List;

public class PizzaResponseDto {
    private Long idPizza;
    private String name;
    private BigDecimal price;
    private String observations;
    private List<FlavorResponseDto> flavors;

    public PizzaResponseDto(Long idPizza, String name, BigDecimal price, String observations, List<FlavorResponseDto> flavors) {
        this.idPizza = idPizza;
        this.name = name;
        this.price = price;
        this.observations = observations;
        this.flavors = flavors;
    }


    public Long getIdPizza() {
        return idPizza;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getObservations() {
        return observations;
    }

    public List<FlavorResponseDto> getFlavors() {
        return flavors;
    }
}
