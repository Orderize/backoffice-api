package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.orderize.backoffice_api.mapper.pizza.PizzaRequestToPizza;
import com.orderize.backoffice_api.mapper.pizza.PizzaToPizzaResponse;
import org.springframework.stereotype.Service;

import com.orderize.backoffice_api.dto.pizza.PizzaRequestDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.repository.FlavorRepository;
import com.orderize.backoffice_api.repository.PizzaRepository;

@Service
public class PizzaService {

    private final PizzaRepository pizzaRepository;
    private final FlavorRepository flavorRepository;

    public PizzaService(
        PizzaRepository pizzaRepository, FlavorRepository flavorRepository
    ) {
        this.pizzaRepository = pizzaRepository;
        this.flavorRepository = flavorRepository;
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    public Pizza getPizzaById(Long id) {
        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza não encontrada"));

        return pizza;
    }

    public Pizza savePizza(Pizza request) {
        return pizzaRepository.save(request);
    }

    public Pizza updatePizza(Long id, Pizza request) {
        if (!pizzaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pizza não encontrada");
        }

        request.setId(id);
        Pizza saved = pizzaRepository.save(request);

        return saved;
    }

    public List<Flavor> getPizzaFlavors(List<Long> ids) {
        return flavorRepository.findAllById(ids);
    }

    public BigDecimal getPrice(List<Flavor> flavors) {
        BigDecimal mostExpensive = flavors.get(0).getPrice();
        for (int i = 1; i < flavors.size(); i++) {
            if (flavors.get(i).getPrice().doubleValue() > mostExpensive.doubleValue()) {
                mostExpensive = flavors.get(i).getPrice();
            }
        }
        return mostExpensive;
    }

    public String getPizzaName(List<Flavor> flavors) {
        String name = "";
        for (int i = 0; i < flavors.size(); i++) {
            String flavorName = flavors.get(i).getName().substring(0, 1).toUpperCase() +
                    flavors.get(i).getName().substring(1).toLowerCase();
            if (i == (flavors.size() - 1) && flavors.size() > 1) {
                name += "e " + flavorName;
            } else if (flavors.size() == 1){
                name += flavorName;
            } else {
                name += flavorName + " ";
            }
        }
        return name;
    }
}
