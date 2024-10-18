package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.promotion.PromotionRequestDto;
import com.orderize.backoffice_api.dto.promotion.PromotionResponseDto;
import com.orderize.backoffice_api.exception.InvalidTimeIntervalException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.promotion.PromotionRequestToPromotion;
import com.orderize.backoffice_api.mapper.promotion.PromotionToPromotionResponse;
import com.orderize.backoffice_api.model.Promotion;
import com.orderize.backoffice_api.model.PromotionCondition;
import com.orderize.backoffice_api.repository.promotion.PromotionConditionRepository;
import com.orderize.backoffice_api.repository.promotion.PromotionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repository;
    private final PromotionToPromotionResponse mapperEntityToResponse;
    private final PromotionRequestToPromotion mapperRequestToEntity;

    public PromotionService(PromotionRepository repository, PromotionToPromotionResponse mapperEntityToResponse, PromotionRequestToPromotion mapperRequestToEntity) {
        this.repository = repository;
        this.mapperEntityToResponse = mapperEntityToResponse;
        this.mapperRequestToEntity = mapperRequestToEntity;
    }

    public List<PromotionResponseDto> getAllPromotions(String name, Boolean valid) {
        List<Promotion> promotions = repository.findAll();

        if (name != null && !name.isBlank()) {
            promotions = promotions.stream().filter(it -> it.getName().equalsIgnoreCase(name)).toList();
        }

        if (valid != null && valid) {
            promotions = promotions.stream().filter(it -> (it.getStartDate().isBefore(LocalDate.now())) && (it.getEndDate().isBefore(LocalDate.now()))).toList();
        }
        if (valid != null && !valid) {
            promotions = promotions.stream().filter(it -> it.getEndDate().isBefore(LocalDate.now())).toList();
        }

        return promotions.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }

    public PromotionResponseDto savePromotion(PromotionRequestDto request) {
        if (!request.endDate().isAfter(request.startDate()) && request.endDate().isAfter(LocalDate.now())) {
            throw new InvalidTimeIntervalException("Intervalo de tempo para promoção inválido");
        }

        Promotion promotionToSave = mapperRequestToEntity.map(request);
        Promotion savedPromotion = repository.save(promotionToSave);
        return mapperEntityToResponse.map(savedPromotion);
    }

    public void deletePromotion(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Promoção não encontrada");
        }

        repository.deleteById(id);
    }

    public PromotionResponseDto updatePromotion(PromotionRequestDto request, Long id) {
        Promotion existingPromotion = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Promoção não encontrada"));

        if (!(request.endDate().isAfter(request.startDate())) || !request.endDate().isAfter(LocalDate.now())) {
            throw new InvalidTimeIntervalException("Intervalo de tempo para promoção inválido");
        }
        existingPromotion.setName(request.name());
        existingPromotion.setDescription(request.description());
        existingPromotion.setStartDate(request.startDate());
        existingPromotion.setEndDate(request.endDate());
        existingPromotion.setDiscountValue(request.discountValue());

        Promotion saved = repository.save(existingPromotion);

        return mapperEntityToResponse.map(saved);
    }
}
