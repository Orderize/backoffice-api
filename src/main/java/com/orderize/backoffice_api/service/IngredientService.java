package com.orderize.backoffice_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.ingredient.IngredientRequestDto;
import com.orderize.backoffice_api.dto.ingredient.IngredientResponseDto;
import com.orderize.backoffice_api.exception.AlreadyExistsException;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.ingredient.IngredientRequestDtoToIngredient;
import com.orderize.backoffice_api.mapper.ingredient.IngredientToIngredientResponseDto;
import com.orderize.backoffice_api.model.Ingredient;
import com.orderize.backoffice_api.repository.IngredientRepository;

@Service
public class IngredientService {

    private final IngredientRepository repository;
    private final IngredientRequestDtoToIngredient mapperRequestToEntity;
    private final IngredientToIngredientResponseDto mapperEntityToResponse;

    public IngredientService(IngredientRepository repository, IngredientRequestDtoToIngredient mapperRequestToEntity, IngredientToIngredientResponseDto mapperEntityToResponse) {
        this.repository = repository;
        this.mapperRequestToEntity = mapperRequestToEntity;
        this.mapperEntityToResponse = mapperEntityToResponse;
    }

    public List<IngredientResponseDto> getAllIngredients() {
        List<Ingredient> allIngredients = repository.findAll();

        return allIngredients.stream().map(it -> mapperEntityToResponse.map(it)).toList();
    }


    public IngredientResponseDto getIngredientById(Long id) {
        Optional<Ingredient> possibleIngredient = repository.findById(id);

        if (possibleIngredient.isPresent()) {
            return mapperEntityToResponse.map(possibleIngredient.get());
        } else {
            throw new ResourceNotFoundException("Ingrediente não encontrado");
        }
    }


    public IngredientResponseDto saveIngredient(IngredientRequestDto request) {
        Ingredient ingredientToSave = mapperRequestToEntity.map(request);

        if (repository.existsByName(ingredientToSave.getName())) {
            throw new AlreadyExistsException("Já existe um ingrediente com este nome");
        }

        Ingredient savedIngredient = repository.save(ingredientToSave);
        return mapperEntityToResponse.map(savedIngredient);
    }

    public IngredientResponseDto updateIngredient(Long id, IngredientRequestDto request) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente não encontrado");
        }

        Ingredient ingredientToSave = mapperRequestToEntity.map(request);
        ingredientToSave.setId(id);

        Ingredient updatedIngredient = repository.save(ingredientToSave);
        return mapperEntityToResponse.map(updatedIngredient);
    }

    public void deleteIngredient(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ingrediente não encontrado");
        }

        repository.deleteById(id);
    }

}
