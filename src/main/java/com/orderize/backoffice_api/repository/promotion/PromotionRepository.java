package com.orderize.backoffice_api.repository.promotion;

import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
