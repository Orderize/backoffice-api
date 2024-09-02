package com.orderize.backoffice_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String state;
    private Integer number;
    private String street;
    private String city;
    private String neighborhood;

    public Address() {}

    public Address(String cep, String state, Integer number, String street, String city, String neighborhood) {
        this.cep = cep;
        this.state = state;
        this.number = number;
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public Address(Long id, String cep, String state, Integer number, String street, String city, String neighborhood) {
        this.id = id;
        this.cep = cep;
        this.state = state;
        this.number = number;
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
}
