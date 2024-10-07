package com.orderize.backoffice_api.mapper;

import com.orderize.backoffice_api.dto.drink.DrinkRequestDto;
import com.orderize.backoffice_api.model.Drink;
import org.springframework.stereotype.Component;

@Component
public class DrinkRequestToDrink implements Mapper<DrinkRequestDto, Drink> {

    @Override
    public Drink map(DrinkRequestDto drinkRequestDto) {
        return new Drink(
                null,
                drinkRequestDto.name(),
                drinkRequestDto.description(),
                drinkRequestDto.price(),
                drinkRequestDto.milimeters()
        );
    }

}
