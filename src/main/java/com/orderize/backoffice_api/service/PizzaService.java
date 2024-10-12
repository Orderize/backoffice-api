package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.PizzaRequestToPizza;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final PizzaRequestToPizza pizzaRequestToPizza;
    private final Pizza pizzaToPizzaResponse;

    public PizzaService(PizzaRepository pizzaRepository,
                        PizzaRequestToPizza pizzaRequestToPizza,
                        Pizza pizzaToPizzaResponse) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaRequestToPizza = pizzaRequestToPizza;
        this.pizzaToPizzaResponse = pizzaToPizzaResponse;
    }

    public List<PizzaResponseDto> getAllPizzas(String name) {
        List<Pizza> pizzas;
        if (name != null && !name.isEmpty()) {
            pizzas = pizzaRepository.findAll().stream()
                    .filter(pizza -> pizza.getName().equalsIgnoreCase(name))
                    .collect(Collectors.toList());
        } else {
            pizzas = pizzaRepository.findAll();
        }
        return pizzas.stream()
                .map(pizzaToPizzaResponse::map)
                .collect(Collectors.toList());
    }

    public PizzaResponseDto getPizzaById(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada com ID: " + id));

        return pizzaToPizzaResponse.map(pizza);
    }

    public PizzaResponseDto savePizza(PizzaRequestDto requestDto) {
        if (requestDto.getName() == null || requestDto.getPrice() == null || requestDto.getDescription() == null) {
            throw new IllegalArgumentException("Os campos nome, preço e descrição são obrigatórios.");
        }

        Pizza pizza = pizzaRequestToPizza.map(requestDto);
        Pizza savedPizza = pizzaRepository.save(pizza);

        return pizzaToPizzaResponse.map(savedPizza);
    }


    public PizzaResponseDto updatePizza(Long id, PizzaRequestDto request) {
        Pizza existingPizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pizza not found with id: " + id));
        Pizza updatedPizza = pizzaRequestToPizza.map(request);
        updatedPizza.setIdPizza(existingPizza.getIdPizza());
        pizzaRepository.save(updatedPizza);
        return pizzaToPizzaResponse.map(updatedPizza);
    }

    public void deletePizza(Long id) {
        if (!pizzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza não encontrada com ID: " + id);
        }
        pizzaRepository.deleteById(id);
    }
}
