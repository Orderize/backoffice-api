package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.promotion.PromotionRequestDto;
import com.orderize.backoffice_api.dto.promotion.PromotionResponseDto;
import com.orderize.backoffice_api.exception.InvalidTimeIntervalException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.promotion.PromotionRequestToPromotion;
import com.orderize.backoffice_api.mapper.promotion.PromotionToPromotionResponse;
import com.orderize.backoffice_api.model.Promotion;
import com.orderize.backoffice_api.repository.promotion.PromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PromotionServiceTest {

    @Mock
    private PromotionRepository repository;

    @Mock
    private PromotionToPromotionResponse mapperEntityToResponse;

    @Mock
    private PromotionRequestToPromotion mapperRequestToEntity;

    @InjectMocks
    private PromotionService promotionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Ao buscar todas as promoções com sucesso")
    void testGetAllPromotions_Success() {
        Promotion promotion1 = new Promotion(1L, "Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), List.of());
        Promotion promotion2 = new Promotion(2L, "Promo 2", "Descrição 2", BigDecimal.valueOf(20.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), List.of());
        PromotionResponseDto responseDto1 = new PromotionResponseDto(1L, "Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), List.of());
        PromotionResponseDto responseDto2 = new PromotionResponseDto(2L, "Promo 2", "Descrição 2", BigDecimal.valueOf(20.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), List.of());

        when(repository.findAll()).thenReturn(List.of(promotion1, promotion2));
        when(mapperEntityToResponse.map(promotion1)).thenReturn(responseDto1);
        when(mapperEntityToResponse.map(promotion2)).thenReturn(responseDto2);

        List<PromotionResponseDto> result = promotionService.getAllPromotions(null, null);

        assertEquals(2, result.size());
        assertEquals("Promo 1", result.get(0).name());
        assertEquals("Promo 2", result.get(1).name());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Ao salvar uma promoção válida")
    void testSavePromotion_Success() {
        PromotionRequestDto requestDto = new PromotionRequestDto("Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now(), LocalDate.now().plusDays(5), List.of());
        Promotion promotion = new Promotion(null, "Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now(), LocalDate.now().plusDays(5), List.of());
        PromotionResponseDto responseDto = new PromotionResponseDto(1L, "Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now(), LocalDate.now().plusDays(5), List.of());

        when(mapperRequestToEntity.map(requestDto)).thenReturn(promotion);
        when(repository.save(promotion)).thenReturn(promotion);
        when(mapperEntityToResponse.map(promotion)).thenReturn(responseDto);

        PromotionResponseDto result = promotionService.savePromotion(requestDto);

        assertEquals("Promo 1", result.name());
        verify(repository).save(promotion);
    }

    @Test
    @DisplayName("Ao atualizar uma promoção válida")
    void testUpdatePromotion_Success() {
        PromotionRequestDto requestDto = new PromotionRequestDto("Promo Atualizada", "Descrição Atualizada", BigDecimal.valueOf(15.0), LocalDate.now(), LocalDate.now().plusDays(10), List.of());
        Promotion existingPromotion = new Promotion(1L, "Promo 1", "Descrição 1", BigDecimal.valueOf(10.0), LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), List.of());
        Promotion updatedPromotion = new Promotion(1L, "Promo Atualizada", "Descrição Atualizada", BigDecimal.valueOf(15.0), LocalDate.now(), LocalDate.now().plusDays(10), List.of());
        PromotionResponseDto responseDto = new PromotionResponseDto(1L, "Promo Atualizada", "Descrição Atualizada", BigDecimal.valueOf(15.0), LocalDate.now(), LocalDate.now().plusDays(10), List.of());

        when(repository.findById(1L)).thenReturn(Optional.of(existingPromotion));
        when(repository.save(existingPromotion)).thenReturn(updatedPromotion);
        when(mapperEntityToResponse.map(updatedPromotion)).thenReturn(responseDto);

        PromotionResponseDto result = promotionService.updatePromotion(requestDto, 1L);

        assertEquals("Promo Atualizada", result.name());
        verify(repository).save(existingPromotion);
    }

    @Test
    @DisplayName("Ao tentar atualizar uma promoção que não existe")
    void testUpdatePromotion_NotFound() {
        PromotionRequestDto requestDto = new PromotionRequestDto("Promo Atualizada", "Descrição Atualizada", BigDecimal.valueOf(15.0), LocalDate.now(), LocalDate.now().plusDays(10), List.of());

        when(repository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            promotionService.updatePromotion(requestDto, 1L);
        });

        assertEquals("Promoção não encontrada", exception.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao excluir uma promoção válida")
    void testDeletePromotion_Success() {
        when(repository.existsById(1L)).thenReturn(true);

        promotionService.deletePromotion(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    @DisplayName("Ao tentar excluir uma promoção que não existe")
    void testDeletePromotion_NotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            promotionService.deletePromotion(1L);
        });

        assertEquals("Promoção não encontrada", exception.getMessage());
        verify(repository, never()).deleteById(1L);
    }

}
