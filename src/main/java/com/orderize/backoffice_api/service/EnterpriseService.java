package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.enterprise.EnterpriseRequestDto;
import com.orderize.backoffice_api.dto.enterprise.EnterpriseResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.EnterpriseRequestToEnterprise;
import com.orderize.backoffice_api.mapper.EnterpriseToEnterpriseResponse;
import com.orderize.backoffice_api.model.Enterprise;
import com.orderize.backoffice_api.repository.EnterpriseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnterpriseService {

    private final EnterpriseRepository repository;
    private final EnterpriseRequestToEnterprise mapperRequestToEntity;
    private final EnterpriseToEnterpriseResponse mapperEntityToResponse;

    public EnterpriseService(EnterpriseRepository repository, EnterpriseRequestToEnterprise mapperRequestToEntity, EnterpriseToEnterpriseResponse mapperEntityToResponse) {
        this.repository = repository;
        this.mapperRequestToEntity = mapperRequestToEntity;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    public List<EnterpriseResponseDto> getAllEnterprises(String name) {
        List<Enterprise> allEnterprises = repository.findAll();

        if (name != null && !name.isBlank()) {
            allEnterprises = allEnterprises.stream().filter(it -> it.getName().equals(name)).toList();
        }

        return allEnterprises.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }


    public EnterpriseResponseDto getEnterpriseById(Long id) {
        Optional<Enterprise> possibleEnterprise = repository.findById(id);

        if (possibleEnterprise.isPresent()) {
            return mapperEntityToResponse.map(possibleEnterprise.get());
        } else {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }
    }


    public EnterpriseResponseDto saveEnterprise(EnterpriseRequestDto request) {
        Enterprise enterpriseToSave = mapperRequestToEntity.map(request);

        if (repository.existsByName(enterpriseToSave.getName())) {
            throw new AlreadyExistsException("Já existe uma empresa com este nome");
        }
        if (repository.existsByCnpj(enterpriseToSave.getCnpj())) {
            throw new AlreadyExistsException("Já existe uma empresa com este CNPJ");
        }

        Enterprise savedEnterprise = repository.save(enterpriseToSave);
        return mapperEntityToResponse.map(savedEnterprise);
    }

    public EnterpriseResponseDto updateEnterprise(Long id, EnterpriseRequestDto request) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }

        Enterprise enterpriseToSave = mapperRequestToEntity.map(request);
        enterpriseToSave.setId(id);

        Enterprise updatedEnterprise = repository.save(enterpriseToSave);
        return mapperEntityToResponse.map(updatedEnterprise);
    }

    public void deleteEnterprise(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Empresa não encontrada");
        }

        repository.deleteById(id);
    }

}
