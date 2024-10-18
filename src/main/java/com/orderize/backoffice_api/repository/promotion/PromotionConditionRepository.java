package com.orderize.backoffice_api.repository.promotion;

import com.orderize.backoffice_api.model.PromotionCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionConditionRepository extends JpaRepository<PromotionCondition, PromotionCondition.PromotionConditionId> {
}
