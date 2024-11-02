package com.orderize.backoffice_api.repository;

import com.orderize.backoffice_api.dto.attestation.AttestationRequestDto;
import com.orderize.backoffice_api.model.Attestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttestationRepository extends JpaRepository<Attestation, Long> {

    List<Attestation> findAllByCreatedTimeBetween(LocalDate startDate, LocalDate endDate);

    List<Attestation> findAllByCreatedTime(LocalDate date);

    Optional<Attestation> findByOrderId(Long id);

    boolean existsByOrderId(Long id);

}
