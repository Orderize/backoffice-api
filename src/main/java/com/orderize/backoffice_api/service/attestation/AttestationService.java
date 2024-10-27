package com.orderize.backoffice_api.service.attestation;

import com.orderize.backoffice_api.dto.attestation.AttestationRequestDto;
import com.orderize.backoffice_api.dto.attestation.AttestationResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.InvalidTimeIntervalException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.attestation.AttestationRequestToAttestation;
import com.orderize.backoffice_api.mapper.attestation.AttestationToAttestationResponse;
import com.orderize.backoffice_api.model.Attestation;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.repository.AttestationRepository;
import com.orderize.backoffice_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttestationService {

    // TODO: REFATORAR ISSO AQUI, O ATTESTATIONSERVICE DEVE INJETAR O ORDERSERVICE E NÃO ORDERREPOSITORY DIRETAMENTE
    private final AttestationRepository repository;
    private final OrderRepository orderRepository;
    private final AttestationToAttestationResponse mapperEntityToResponse;
    private final AttestationRequestToAttestation mapperRequestToEntity;

    public AttestationService(AttestationRepository repository, OrderRepository orderRepository, AttestationToAttestationResponse mapperEntityToResponse, AttestationRequestToAttestation mapperRequestToEntity) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.mapperEntityToResponse = mapperEntityToResponse;
        this.mapperRequestToEntity = mapperRequestToEntity;
    }

    public List<AttestationResponseDto> getAllAttestations(LocalDate date, LocalDate startDate, LocalDate endDate) {
        List<Attestation> attestations;

        if (date != null) {
            attestations = repository.findAllByCreatedTime(date);
        } else if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new InvalidTimeIntervalException("Intervalo de tempo inválido");
            }
            attestations = repository.findAllByCreatedTimeBetween(startDate, endDate);
        } else {
            attestations = repository.findAll();
        }

        return attestations.stream().map(it ->
                mapperEntityToResponse.map(it)).toList();
    }

    public AttestationResponseDto getAttestationByOrderId(Long id) {
        Attestation attestation = repository.findByOrderId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprovante não encontrado"));

        return mapperEntityToResponse.map(attestation);
    }

    public AttestationResponseDto saveAttestation(AttestationRequestDto request) {
        if (repository.existsByOrderId(request.order())) {
            throw new AlreadyExistsException("Já existe um comprovante para este pedido");
        }

        Order order = orderRepository.findById(request.order()).orElseThrow(
                () -> new ResourceNotFoundException("Pedido referente ao comprovante não encontrado")
        );

        Attestation entityToSave = mapperRequestToEntity.map(request);
        entityToSave.setOrder(order);

        Attestation savedAttestation = repository.save(entityToSave);
        return mapperEntityToResponse.map(savedAttestation);
    }

    public void deleteAttestation(Long id) {
        Attestation attestation = repository.findByOrderId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comprovante não encontrado"));
        repository.delete(attestation);
    }

}
