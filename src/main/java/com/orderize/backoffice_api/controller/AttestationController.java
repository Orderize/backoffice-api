package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.attestation.AttestationRequestDto;
import com.orderize.backoffice_api.dto.attestation.AttestationResponseDto;
import com.orderize.backoffice_api.dto.csv.CsvResponseDto;
import com.orderize.backoffice_api.model.Attestation;
import com.orderize.backoffice_api.repository.AttestationRepository;
import com.orderize.backoffice_api.service.attestation.AttestationService;
import com.orderize.backoffice_api.util.CsvFileUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/attestations", produces = {"application/json"})
public class AttestationController {

    private final AttestationService service;
    private final AttestationRepository repository;

    public AttestationController(AttestationService service, AttestationRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    @Operation(
            summary = "Busca todos os comprovantes",
            description = "Pode receber os request param opcionais: [date] OU [startDate, endDate]" +
                    " e filtra o resultado com base nos param passados, caso passe date busca os comprovantes daquela" +
                    " data específica, caso passe startDate e endDate busca com base no intervalo de tempo designado, " +
                    "caso nenhum seja passado retorna todos os comprovantes.",
            method = "GET"
    )
    public ResponseEntity<List<AttestationResponseDto>> getAllAttestations(
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        List<AttestationResponseDto> attestations = service.getAllAttestations(date, startDate, endDate);

        if (attestations.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(attestations);
        }
    }

    /*
    Só existe um attestation por order, e orderId é uma informação que o front tera mais facilmente, então faz sentido
    buscar pelo id da Order invés do id da Attestation
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Busca um comprovante utilizando o Id do pedido correspondente",
            method = "GET"
    )
    public ResponseEntity<AttestationResponseDto> getAttestationByOrderId(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(200).body(service.getAttestationByOrderId(id));
    }

    @PostMapping
    @Operation(
            summary = "Salva um novo comprovante válido",
            method = "POST"
    )
    public ResponseEntity<AttestationResponseDto> saveAttestation(
            @RequestBody @Valid AttestationRequestDto request
    ) {
        return ResponseEntity.status(201).body(service.saveAttestation(request));
    }

    // just registering: attestation não tem endpoint para atualizar pq não faz muito sentido poder atualizar comprovantes

    /*
    Pelo mesmo motivo do getByOrderId creio que faz mais sentido deletar o Attestation utilizando o id da Order
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deleta um attestation",
            description = "Deleta o attestation recebendo como parâmetro o id do próprio attestation",
            method = "DELETE"
    )
    public ResponseEntity<Void> deleteAttestationByOrderId(
            @PathVariable("id") Long id
    ) {
        service.deleteAttestation(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/csv/download")
    @Operation(
            summary = "Criação e download de arquivo csv com attestations",
            description = "Pode receber os request param opcionais: [date] OU [startDate, endDate]" +
                    " e filtra o resultado com base nos param passados, caso passe date busca os comprovantes daquela" +
                    " data específica, caso passe startDate e endDate busca com base no intervalo de tempo designado, " +
                    "caso nenhum seja passado serão adicionados no arquivo csv todos os comprovantes.",
            method = "GET"
    )
    public ResponseEntity<InputStreamResource> downloadCsv(
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        CsvResponseDto resource = service.writeCsvFile(date, startDate, endDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.file().getName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(resource.file().length())
                .body(resource.resource());
    }

}
