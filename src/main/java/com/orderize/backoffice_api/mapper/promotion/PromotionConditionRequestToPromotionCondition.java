package com.orderize.backoffice_api.mapper.promotion;

import com.orderize.backoffice_api.dto.promotion.PromotionConditionRequestDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.ConditionType;
import com.orderize.backoffice_api.model.Promotion;
import com.orderize.backoffice_api.model.PromotionCondition;
import com.orderize.backoffice_api.repository.promotion.ConditionTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class PromotionConditionRequestToPromotionCondition implements Mapper<PromotionConditionRequestDto, PromotionCondition> {

    private final ConditionTypeRepository repository;

    public PromotionConditionRequestToPromotionCondition(ConditionTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public PromotionCondition map(PromotionConditionRequestDto promotionConditionRequestDto) {
        ConditionType conditionType = repository.findById(promotionConditionRequestDto.conditionType())
                .orElseThrow(() -> new ResourceNotFoundException("ConditionType não encontrado"));
        return new PromotionCondition(
                conditionType,
                promotionConditionRequestDto.requiredValue(),
                promotionConditionRequestDto.idsRequiredItens()
        );
    }

}
