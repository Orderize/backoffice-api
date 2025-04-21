package com.orderize.backoffice_api.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fk_client")
    private User client;

    @ManyToOne(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "fk_responsible")
    private User responsible;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "order_pizza", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<Pizza> pizzas;

    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "order_drink", 
        joinColumns = @JoinColumn(name = "order_id"), 
        inverseJoinColumns = @JoinColumn(name = "drink_id")
    )
    private List<Drink> drinks;

    @CurrentTimestamp
    private Instant datetime;

    private String type;
    private BigDecimal freight;

    @Column(name = "estimated_time")
    private Integer estimatedTime;
    private BigDecimal price;

    private String status; 

    @Column(name = "`table`")
    private Integer table;
     
    @LastModifiedDate
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

}
