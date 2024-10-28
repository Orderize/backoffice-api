package com.orderize.backoffice_api.mapper.attestation;

import com.orderize.backoffice_api.dto.attestation.AttestationRequestDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Attestation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AttestationRequestToAttestation implements Mapper<AttestationRequestDto, Attestation> {

    @Override
    public Attestation map(AttestationRequestDto requestDto) {
        return new Attestation(
                null,
                LocalDate.now(),
                null
        );
    }

}
