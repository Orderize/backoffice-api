package com.orderize.backoffice_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.pizza.PizzaRequestDtoToPizza;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponseDto;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.repository.FlavorRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaRequestDtoToPizza mapperPizzaRequestToPizza;
    private final PizzaToPizzaResponseDto mapperPizzaToPizzaResponse;
    private final FlavorRepository flavorRepository;

    public PizzaService(
        PizzaRepository pizzaRepository, PizzaRequestDtoToPizza mapperPizzaRequestToPizza, PizzaToPizzaResponseDto mapperPizzaToPizzaResponse, FlavorRepository flavorRepository
    ) {
        this.pizzaRepository = pizzaRepository;
        this.mapperPizzaRequestToPizza = mapperPizzaRequestToPizza;
        this.mapperPizzaToPizzaResponse = mapperPizzaToPizzaResponse;
        this.flavorRepository = flavorRepository;
    }

    public List<PizzaResponseDto> getAllPizzas() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        if (pizzas.isEmpty()) throw new ResourceNotFoundException("Pizza não encontrada"); 

        return pizzas.stream()
                .map(it -> mapperPizzaToPizzaResponse.map(it))
                .collect(Collectors.toList());
    }

    public List<PizzaResponseDto> getAllPizzas(List<Long> ids) {
        List<Pizza> pizzas = pizzaRepository.findAllById(ids);

        if (pizzas.isEmpty()) throw new ResourceNotFoundException("Pizzas não encontradas");

        return pizzas.stream().map(it -> mapperPizzaToPizzaResponse.map(it)).toList();
    }

    public PizzaResponseDto getPizzaById(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada com ID: " + id));

        return mapperPizzaToPizzaResponse.map(pizza);
    }

    public PizzaResponseDto savePizza(PizzaRequestDto requestDto) {
        if (requestDto.name() == null || requestDto.price() == null || requestDto.observations() == null || requestDto.flavor() == null) {
            throw new IllegalArgumentException("Os campos nome, preço, descrição e sabor são obrigatórios.");
        }

        Flavor flavor = flavorRepository.findById(requestDto.flavor()).get();

        Pizza pizza = mapperPizzaRequestToPizza.map(requestDto, flavor);

        Pizza savedPizza = pizzaRepository.save(pizza);

        return mapperPizzaToPizzaResponse.map(savedPizza);
    }

    // precisa conseguir inserir sabor e tirar sabor
    // deve permitir uma lista de sabores para adicionar ou excluir
    public PizzaResponseDto updatePizza(Long id, PizzaRequestDto requestDto) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found with id: " + id));
        
        Flavor flavor = flavorRepository.findById(requestDto.flavor()).get();
        
        Pizza updatedPizza = mapperPizzaRequestToPizza.map(requestDto, existingPizza, flavor);
        pizzaRepository.save(updatedPizza);

        return mapperPizzaToPizzaResponse.map(updatedPizza);
    }

    public void deletePizza(Long id) {
        if (!pizzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza não encontrada com ID: " + id);
        }
        pizzaRepository.deleteById(id);
    }
}
