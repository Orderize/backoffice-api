package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.promotion.PromotionRequestDto;
import com.orderize.backoffice_api.dto.promotion.PromotionResponseDto;
import com.orderize.backoffice_api.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/promotions", produces = {"application/json"})
@Tag(name = "/promotions")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Busca todas as promoções",
            method = "GET",
            description = "Pode receber os request param opcionais: [name, valid]" +
                    " e filtra o resultado com base nos request param" +
                    "passados, caso nenhum seja passado retorna uma list com todas as promoções"
    )
    public ResponseEntity<List<PromotionResponseDto>> getAllPromotions(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "valid", required = false) Boolean valid
    ) {
        List<PromotionResponseDto> promotions = service.getAllPromotions(name, valid);

        if (promotions.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(promotions);
        }
    }

    @PostMapping
    @Operation(
            summary = "Salva uma nova promoção",
            method = "POST",
            description = "Recebe a promoção já com suas condições e salva uma nova promoção válida"
    )
    public ResponseEntity<PromotionResponseDto> postPromotion(
            @RequestBody @Valid PromotionRequestDto request
    ) {
        PromotionResponseDto savedPromotion = service.savePromotion(request);
        return ResponseEntity.status(201).body(savedPromotion);
    }

    @PutMapping("/{id}")
    @Operation(summary = "" +
            "Atualiza os dados de uma promoção, não atualiza conditions da promoção apenas os dados da própria promoção" +
            " [name, description, discountValue, startDate e endDate]"
            , method = "PUT")
    public ResponseEntity<PromotionResponseDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody PromotionRequestDto request
    ) {
        PromotionResponseDto promotion = service.updatePromotion(request, id);
        return ResponseEntity.status(200).body(promotion);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta uma promoção",
            method = "DELETE",
            description = "Deleta uma promoção e suas condições do sistema"
    )
    public ResponseEntity<Void> deletePromotion(
            @PathVariable("id") Long id
    ) {
        service.deletePromotion(id);
        return ResponseEntity.status(204).build();
    }
}
