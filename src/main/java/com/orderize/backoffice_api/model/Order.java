package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

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

    private Timestamp datetime_order;
    private String type;
    private Double freight;
    private Double estimativeTime;
    private Double grossPrice;
    private Double netPrice;

    public Order() {
    }

    public Order(Long id, User client, User responsible, Timestamp datetime_order, String type, Double freight, Double estimativeTime, Double grossPrice, Double netPrice) {
        this.id = id;
        this.client = client;
        this.responsible = responsible;
        this.datetime_order = datetime_order;
        this.type = type;
        this.freight = freight;
        this.estimativeTime = estimativeTime;
        this.grossPrice = grossPrice;
        this.netPrice = netPrice;
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

    public Timestamp getDatetime_order() {
        return datetime_order;
    }

    public void setDatetime_order(Timestamp datetime_order) {
        this.datetime_order = datetime_order;
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

    public Double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(Double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public Double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Double netPrice) {
        this.netPrice = netPrice;
    }
}
