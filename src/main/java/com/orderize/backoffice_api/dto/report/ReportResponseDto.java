package com.orderize.backoffice_api.dto.report;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.dto.pizza.PizzaResponseDto;

import java.math.BigDecimal;

public record ReportResponseDto(
        Long qttOrders,
        BigDecimal revenue,
        BigDecimal averageDailyRevenue,
        String weekDayWithMostOrders,
        FlavorResponseDto mostOrderedFlavor,
        DrinkResponseDto mostOrderedDrink
) {
}
