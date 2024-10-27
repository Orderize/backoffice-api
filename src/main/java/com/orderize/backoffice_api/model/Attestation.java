package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Attestation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate createdTime;
    @OneToOne
    @JoinColumn(name = "fk_order")
    private Order order;

    public Attestation() {
    }

    public Attestation(Long id, LocalDate createdTime, Order order) {
        this.id = id;
        this.createdTime = createdTime;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}