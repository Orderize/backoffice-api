package com.orderize.backoffice_api.mapper.promotion;

import com.orderize.backoffice_api.dto.promotion.PromotionResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Promotion;
import com.orderize.backoffice_api.model.PromotionCondition;
import org.springframework.stereotype.Component;

@Component
public class PromotionToPromotionResponse implements Mapper<Promotion, PromotionResponseDto> {

    private final PromotionConditionToPromotionConditionResponse mapper;

    public PromotionToPromotionResponse(PromotionConditionToPromotionConditionResponse mapper) {
        this.mapper = mapper;
    }

    @Override
    public PromotionResponseDto map(Promotion promotion) {
        return new PromotionResponseDto(
                promotion.getId(),
                promotion.getName(),
                promotion.getDescription(),
                promotion.getDiscountValue(),
                promotion.getStartDate(),
                promotion.getEndDate(),
                promotion.getConditions().stream().map(it -> mapper.map(it)).toList()
        );
    }
}
