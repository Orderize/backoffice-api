package com.orderize.backoffice_api.mapper.promotion;

import com.orderize.backoffice_api.dto.promotion.PromotionRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.ConditionType;
import com.orderize.backoffice_api.model.Promotion;
import com.orderize.backoffice_api.model.PromotionCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PromotionRequestToPromotion implements Mapper<PromotionRequestDto, Promotion> {

    private final PromotionConditionRequestToPromotionCondition mapper;

    public PromotionRequestToPromotion(PromotionConditionRequestToPromotionCondition mapper) {
        this.mapper = mapper;
    }

    @Override
    public Promotion map(PromotionRequestDto promotionRequestDto) {
        List<PromotionCondition> conditions = promotionRequestDto.conditions().stream().map(it -> mapper.map(it)).toList();

        Promotion newPromotion =  new Promotion(
                promotionRequestDto.name(),
                promotionRequestDto.description(),
                promotionRequestDto.discountValue(),
                promotionRequestDto.startDate(),
                promotionRequestDto.endDate()
        );
        conditions.forEach(it -> {
            it.setPromotion(newPromotion);
        });
        newPromotion.setConditions(conditions);
        return newPromotion;
    }

}
