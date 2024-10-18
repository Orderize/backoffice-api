package com.orderize.backoffice_api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ConditionType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    public ConditionType() {}

    public ConditionType(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public ConditionType(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
