package com.orderize.backoffice_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.drink.DrinkRequestDto;
import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.drink.DrinkRequestToDrink;
import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.repository.DrinkRepository;

@Service
public class DrinkService {

    private final DrinkRepository repository;
    private final DrinkRequestToDrink mapperRequestToEntity;
    private final DrinkToDrinkResponse mapperEntityToResponse;

    public DrinkService(DrinkRepository repository, DrinkRequestToDrink mapperRequestToEntity, DrinkToDrinkResponse mapperEntityToResponse) {
        this.repository = repository;
        this.mapperRequestToEntity = mapperRequestToEntity;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    public List<DrinkResponseDto> getAllDrinks(String name, Integer milimeters) {
        List<Drink> allDrinks = repository.findAll();

        if (!allDrinks.isEmpty() && !name.isBlank()) {
            allDrinks = allDrinks.stream().filter(it -> it.getName().equalsIgnoreCase(name)).toList();
        }

        if (!allDrinks.isEmpty() && milimeters != null) {
            allDrinks = allDrinks.stream().filter(it -> it.getMilimeters() == milimeters).toList();
        }

        return allDrinks.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }

    public List<DrinkResponseDto> getAllDrinks(List<Long> ids) {
        List<Drink> allDrinks = repository.findAllById(ids);
        return allDrinks.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }

    public DrinkResponseDto saveDrink(DrinkRequestDto request) {
        if (repository.existsByName(request.name())) {
            throw new AlreadyExistsException("Essa bebida já existe no sistema");
        }

        Drink entityToSave = mapperRequestToEntity.map(request);
        Drink saved = repository.save(entityToSave);
        return mapperEntityToResponse.map(saved);
    }

    public DrinkResponseDto updateDrink(Long id, DrinkRequestDto request) {
        Optional<Drink> possibleDrink = repository.findById(id);

        if (!possibleDrink.isPresent()) {
            throw new ResourceNotFoundException("Bebida para atualizar não encontrada");
        }

        Drink drinkToSave = mapperRequestToEntity.map(request);
        drinkToSave.setId(id);

        Drink saved = repository.save(drinkToSave);
        return mapperEntityToResponse.map(saved);
    }

    public void deleteDrink(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Bebida para deletar não encontrada");
        }

        repository.deleteById(id);
    }
}
