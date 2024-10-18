package com.orderize.backoffice_api.mapper.promotion;

import com.orderize.backoffice_api.dto.promotion.PromotionConditionResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.PromotionCondition;
import org.springframework.stereotype.Component;

@Component
public class PromotionConditionToPromotionConditionResponse implements Mapper<PromotionCondition, PromotionConditionResponseDto> {

    @Override
    public PromotionConditionResponseDto map(PromotionCondition promotionCondition) {
        return new PromotionConditionResponseDto(
                promotionCondition.getId().fkPromotion(),
                promotionCondition.getId().fkConditionType(),
                promotionCondition.getConditionType().getDescription(),
                promotionCondition.getRequiredValue(),
                promotionCondition.getIdsRequiredItens()
        );
    }

}
