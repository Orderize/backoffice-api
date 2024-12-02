package com.orderize.backoffice_api.service;

import com.orderize.backoffice_api.dto.drink.DrinkResponseDto;
import com.orderize.backoffice_api.dto.flavor.FlavorResponseDto;
import com.orderize.backoffice_api.dto.report.ReportResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.*;
import com.orderize.backoffice_api.repository.AttestationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final AttestationRepository repository;
    private final FlavorToFlavorResponseDto mapperFlavorToResponse;
    private final DrinkToDrinkResponse mapperDrinkToResponse;

    public ReportService(AttestationRepository repository, FlavorToFlavorResponseDto mapperFlavorToResponse, DrinkToDrinkResponse mapperDrinkToResponse) {
        this.repository = repository;
        this.mapperFlavorToResponse = mapperFlavorToResponse;
        this.mapperDrinkToResponse = mapperDrinkToResponse;
    }

    public ReportResponseDto getLastMonthReport() {
        LocalDate start = LocalDate.now().minusMonths(1);
        List<Attestation> attestations = repository.findAllByCreatedTimeBetween(start, LocalDate.now());
        int qttDays = (int) ChronoUnit.DAYS.between(start, LocalDate.now());

        if (attestations.isEmpty()) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }

        Long qttOrders = (long) attestations.size();
        FlavorResponseDto flavor = mapperFlavorToResponse.map(getMostOrderedFlavor(attestations));
        DrinkResponseDto drink = mapperDrinkToResponse.map(getMostOrderedDrink(attestations));
        return new ReportResponseDto(
                qttOrders,
                getTotalRevenue(attestations),
                getAverageDailyRevenue(attestations, qttDays),
                getWeekDayWithMostOrders(attestations),
                flavor,
                drink
        );
    }

    public List<ReportResponseDto> getLastMonthWeekReports() {
        LocalDate start = LocalDate.now().minusMonths(1);
        List<Attestation> allAttestations = repository.findAllByCreatedTimeBetween(start, LocalDate.now());

        if (allAttestations.isEmpty()) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }

        Map<Integer, List<Attestation>> attestationsByWeek = allAttestations.stream()
                .collect(Collectors.groupingBy(att -> att.getCreatedTime().get(ChronoField.ALIGNED_WEEK_OF_YEAR)));

        List<ReportResponseDto> reports = new ArrayList<>();

        for (Map.Entry<Integer, List<Attestation>> entry : attestationsByWeek.entrySet()) {
            List<Attestation> weeklyAttestations = entry.getValue();

            Long qttOrders = (long) weeklyAttestations.size();
            FlavorResponseDto flavor = mapperFlavorToResponse.map(getMostOrderedFlavor(weeklyAttestations));
            DrinkResponseDto drink = mapperDrinkToResponse.map(getMostOrderedDrink(weeklyAttestations));
            reports.add(new ReportResponseDto(
                    qttOrders,
                    getTotalRevenue(weeklyAttestations),
                    getAverageDailyRevenue(weeklyAttestations, 7),
                    getWeekDayWithMostOrders(weeklyAttestations),
                    flavor,
                    drink
            ));
        }

        return reports;
    }

    public ReportResponseDto getLastWeekReport() {
        LocalDate start = LocalDate.now().minusWeeks(1);
        List<Attestation> attestations = repository.findAllByCreatedTimeBetween(start, LocalDate.now());

        if (attestations.isEmpty()) {
            throw new ResourceNotFoundException("Dados não encontrados");
        }

        Long qttOrders = (long) attestations.size();
        FlavorResponseDto flavor = mapperFlavorToResponse.map(getMostOrderedFlavor(attestations));
        DrinkResponseDto drink = mapperDrinkToResponse.map(getMostOrderedDrink(attestations));
        return new ReportResponseDto(
                qttOrders,
                getTotalRevenue(attestations),
                getAverageDailyRevenue(attestations, 7),
                getWeekDayWithMostOrders(attestations),
                flavor,
                drink
        );
    }

    public BigDecimal getAverageDailyRevenue(List<Attestation> attestations, int qttDays) {
        Map<LocalDate, BigDecimal> dailyRevenueMap = attestations.stream()
                .collect(Collectors.groupingBy(
                        Attestation::getCreatedTime,
                        Collectors.mapping(
                                it -> it.getOrder().getPrice(),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        BigDecimal totalRevenue = dailyRevenueMap.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalRevenue.divide(BigDecimal.valueOf(qttDays), RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalRevenue(List<Attestation> attestations) {
        return attestations.stream().map(it -> it.getOrder().getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getWeekDayWithMostOrders(List<Attestation> attestations) {
        Map<LocalDate, Long> daysCount = new HashMap<>();

        for (Attestation attestation: attestations) {
            daysCount.put(attestation.getCreatedTime(), daysCount.getOrDefault(attestation.getCreatedTime(), 0L) + 1);
        }

        LocalDate date = daysCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        if (date != null) {
            return date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
        } else {
            return null;
        }
    }

    public Flavor getMostOrderedFlavor(List<Attestation> attestations) {
        Map<Flavor, Long> flavorCount = new HashMap<>();

        for (Attestation attestation : attestations) {
            Order order = attestation.getOrder();
            if (order != null && !order.getPizzas().isEmpty() && order.getPizzas() != null) {
                for (Pizza pizza : order.getPizzas()) {
                    if (pizza.getFlavors() != null) {
                        for (Flavor flavor : pizza.getFlavors()) {
                            flavorCount.put(flavor, flavorCount.getOrDefault(flavor, 0L) + 1);
                        }
                    }
                }
            }
        }

        return flavorCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Drink getMostOrderedDrink(List<Attestation> attestations) {
        Map<Drink, Long> drinkCount = new HashMap<>();

        for (Attestation attestation: attestations) {
            Order order = attestation.getOrder();
            if (order != null && !order.getDrinks().isEmpty() && order.getDrinks() != null) {
                for (Drink drink: order.getDrinks()) {
                    drinkCount.put(drink, drinkCount.getOrDefault(drink, 0L) +1);
                }
            }
        }

        return drinkCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


}
