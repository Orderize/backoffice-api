package com.orderize.backoffice_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.flavor.FlavorRequestDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.flavor.FlavorRequestToFlavor;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponse;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.repository.FlavorRepository;

@Service
public class FlavorService {

    private final FlavorRepository repository;
    private final FlavorRequestToFlavor mapperRequestToEntity;
    private final FlavorToFlavorResponse mapperEntityToResponse;

    public FlavorService(FlavorRepository repository, FlavorRequestToFlavor mapperRequestToEntity, FlavorToFlavorResponse mapperEntityToResponse) {
        this.repository = repository;
        this.mapperRequestToEntity = mapperRequestToEntity;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    public List<FlavorResponseDto> getAllFlavors() {
        List<Flavor> allFlavors = repository.findAll();

        return allFlavors.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }


    public FlavorResponseDto getFlavorById(Long id) {
        Optional<Flavor> possibleFlavor = repository.findById(id);

        if (possibleFlavor.isPresent()) {
            return mapperEntityToResponse.map(possibleFlavor.get());
        } else {
            throw new ResourceNotFoundException("Sabor não encontrada");
        }
    }


    public FlavorResponseDto saveFlavor(FlavorRequestDto request) {
        Flavor flavorToSave = mapperRequestToEntity.map(request);

        if (repository.existsByName(flavorToSave.getName())) {
            throw new AlreadyExistsException("Já existe uma sabor com este nome");
        }

        Flavor savedFlavor = repository.save(flavorToSave);
        return mapperEntityToResponse.map(savedFlavor);
    }

    public FlavorResponseDto updateFlavor(Long id, FlavorRequestDto request) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Sabor não encontrada");
        }

        Flavor flavorToSave = mapperRequestToEntity.map(request);
        flavorToSave.setId(id);

        Flavor updatedFlavor = repository.save(flavorToSave);
        return mapperEntityToResponse.map(updatedFlavor);
    }

    public void deleteFlavor(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Sabor não encontrada");
        }

        repository.deleteById(id);
    }

}
