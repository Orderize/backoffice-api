package com.orderize.backoffice_api.mapper.drink;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.mapper.Mapper;
import com.orderize.backoffice_api.model.Drink;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DrinkToDrinkResponse implements Mapper<Drink, DrinkResponseDto> {

    @Override
    public DrinkResponseDto map(Drink drink) {
        BigDecimal formattedPrice = drink.getPrice().setScale(2, RoundingMode.HALF_EVEN);

        return new DrinkResponseDto(
                drink.getId(),
                drink.getName(),
                drink.getDescription(),
                formattedPrice,
                drink.getMilimeters()
        );
    }

}
