package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private User client;

    @ManyToOne
    @JoinColumn(name = "responsible")
    private User responsible;

    private String type;
    private Double freight;
    private Double estimativeTime;

    public Order() {
    }

    public Order(User client, User responsible, String type, Double freight, Double estimativeTime) {
        this.id = id;
        this.client = client;
        this.responsible = responsible;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
    }

    public Order(Long id, User client, User responsible, String type, Double freight, Double estimativeTime) {
        this.id = id;
        this.client = client;
        this.responsible = responsible;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
    }

    public Order(Long id, String type, Double freight, Double estimativeTime){
        this.id = id;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
    }

    public Order(Long id, Long client, Long responsible, String type, Double freight, Double estimativeTime) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getEstimativeTime() {
        return estimativeTime;
    }

    public void setEstimativeTime(Double estimativeTime) {
        this.estimativeTime = estimativeTime;
    }
}
