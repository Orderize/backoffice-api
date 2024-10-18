package com.orderize.backoffice_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "promotion_condition")
public class PromotionCondition {

    @EmbeddedId
    private PromotionConditionId id;

    @ManyToOne // Adicione esta anotação
    @MapsId("fkPromotion") // Isso liga o id fkPromotion ao campo da classe
    @JoinColumn(name = "fk_promotion")
    private Promotion promotion; // Propriedade de referência

    @ManyToOne // Adicione esta anotação
    @MapsId("fkConditionType") // Isso liga o id fkPromotion ao campo da classe
    @JoinColumn(name = "fk_condition_type")
    private ConditionType conditionType;

    private BigDecimal requiredValue;
    private String idsRequiredItens;

    public PromotionCondition() {}

    public PromotionCondition(PromotionConditionId id, BigDecimal requiredValue, String idsRequiredItens) {
        this.id = id;
        this.requiredValue = requiredValue;
        this.idsRequiredItens = idsRequiredItens;
    }

    public PromotionCondition(PromotionConditionId id, Promotion promotion, ConditionType conditionType, BigDecimal requiredValue, String idsRequiredItens) {
        this.id = id;
        this.promotion = promotion;
        this.conditionType = conditionType;
        this.requiredValue = requiredValue;
        this.idsRequiredItens = idsRequiredItens;
    }

    public PromotionCondition(ConditionType conditionType, BigDecimal requiredValue, String idsRequiredItens) {
        this.conditionType = conditionType;
        this.requiredValue = requiredValue;
        this.idsRequiredItens = idsRequiredItens;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public PromotionConditionId getId() {
        return id;
    }

    public void setId(PromotionConditionId id) {
        this.id = id;
    }

    public BigDecimal getRequiredValue() {
        return requiredValue;
    }

    public void setRequiredValue(BigDecimal requiredValue) {
        this.requiredValue = requiredValue;
    }

    public String getIdsRequiredItens() {
        return idsRequiredItens;
    }

    public void setIdsRequiredItens(String idsRequiredItens) {
        this.idsRequiredItens = idsRequiredItens;
    }

    @Embeddable
    public record PromotionConditionId(
            @Column(name = "fk_promotion")
            Long fkPromotion,
            @Column(name = "fk_condition_type")
            Long fkConditionType
    ) {
    }

    @Override
    public String toString() {
        return "PromotionCondition{" +
                "id=" + id +
                ", requiredValue=" + requiredValue +
                ", idsRequiredItens='" + idsRequiredItens + '\'' +
                '}';
    }
}
