package com.orderize.backoffice_api.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.orderize.backoffice_api.dto.report.ReportResponseDto;
import com.orderize.backoffice_api.exception.ResourceNotFoundException;
import com.orderize.backoffice_api.mapper.drink.DrinkToDrinkResponse;
import com.orderize.backoffice_api.mapper.flavor.FlavorToFlavorResponseDto;
import com.orderize.backoffice_api.model.Attestation;
import com.orderize.backoffice_api.model.Drink;
import com.orderize.backoffice_api.model.Flavor;
import com.orderize.backoffice_api.model.Order;
import com.orderize.backoffice_api.model.Pizza;
import com.orderize.backoffice_api.model.User;
import com.orderize.backoffice_api.repository.AttestationRepository;

public class ReportServiceTest {

    @Mock
    AttestationRepository repository;

    @Mock
    FlavorToFlavorResponseDto mapperFlavorToResponse;

    @Mock
    DrinkToDrinkResponse mapperDrinkToResponse;

    @InjectMocks
    ReportService service;

    private static List<Attestation> attestations;

    private static List<Order> orders;

    private static List<Drink> drinks;

    private static List<Pizza> pizzas;

    private static List<Flavor> flavors;

    private static List<User> users;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        flavors = List.of(
                new Flavor(
                        "Marguerita",
                        "Sabor gostoso",
                        BigDecimal.valueOf(128.0)
                ),
                new Flavor(
                        "Mussarela",
                        "Sabor gostoso",
                        BigDecimal.valueOf(130.0)
                ),
                new Flavor(
                        "Frango com catupiry",
                        "Sabor gostoso",
                        BigDecimal.valueOf(12.0)
                )
        );

        pizzas = List.of(
                new Pizza(
                        1L,
                        "Marguerita",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        List.of(flavors.get(0))
                ),
                new Pizza(
                        2L,
                        "JHVJHVV",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        flavors
                ),
                new Pizza(
                        3L,
                        "fsdfsdffreg",
                        BigDecimal.valueOf(128.0),
                        "Muitas observacoes",
                        flavors
                )
        );

        drinks = List.of(
                new Drink(
                        1L,
                        "Suco de tamarindo",
                        "Um suco muito do bão",
                        BigDecimal.valueOf(25.0),
                        350
                ),
                new Drink(
                        2L,
                        "Suco de laranja",
                        "Um suco muito do bão",
                        BigDecimal.valueOf(33.0),
                        350
                )
        );

        users = List.of(
                new User(),
                new User()
        );

        orders = List.of(
                new Order(
                        1L,
                        users.get(0),
                        users.get(1),
                        pizzas,
                        drinks,
                        Instant.now(),
                        "delivery",
                        BigDecimal.valueOf(10.0),
                        10,
                        BigDecimal.valueOf(100.0)
                ),
                new Order(
                        2L,
                        users.get(0),
                        users.get(1),
                        List.of(pizzas.get(0)),
                        List.of(drinks.get(0)),
                        Instant.now(),
                        "delivery",
                        BigDecimal.valueOf(10.0),
                        10,
                        BigDecimal.valueOf(80.0)
                ),
                new Order(
                        3L,
                        users.get(0),
                        users.get(1),
                        List.of(pizzas.get(0), pizzas.get(1)),
                        List.of(drinks.get(0)),
                        Instant.now(),
                        "delivery",
                        BigDecimal.valueOf(10.0),
                        10,
                        BigDecimal.valueOf(150.0)
                )
        );

        attestations = List.of(
                new Attestation(
                        1L,
                        LocalDate.now(),
                        orders.get(0)
                ),
                new Attestation(
                        2L,
                        LocalDate.now().minusMonths(1),
                        orders.get(1)
                ),
                new Attestation(
                        3L,
                        LocalDate.now().minusWeeks(2),
                        orders.get(2)
                ),
                new Attestation(
                        3L,
                        LocalDate.now().minusWeeks(1),
                        orders.get(2)
                )
        );
    }

    @Test
    @DisplayName("getLastMonthReport quando invocado e houverem dados disponíveis deve retornar um report sobre o último mês")
    void getLastMonthReportQuandoInvocadoEHouveremDadosDisponiveisDeveRetornarUmReportSobreOUltimoMesTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(List.of(attestations.get(0), attestations.get(2)));

        ReportResponseDto result = service.getLastMonthReport();

        assertNotNull(result);
        assertEquals(2, result.qttOrders());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getLastMonthReport quando invocado e não houverem dados deve lançar ResourceNotFoundException")
    void getLastMonthReportQuandoInvocadoENaoHouveremDadosDeveLancarResourceNotFoundExceptionTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getLastMonthReport()
        );

        assertEquals("Dados não encontrados", exception.getMessage());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getLastMonthWeekReports deve retornar uma lista com os reports referentes a cada semana do mês")
    void getLastMonthWeekReportsDeveRetornarUmaListaComOsReportsReferentesACadaSemanaDoMesTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(attestations);

        List<ReportResponseDto> result = service.getLastMonthWeekReports();

        assertEquals(4, result.size());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getLastMonthWeekReports quando for invocado e não houverem dados disponíveis deve lançar ResourceNotFoundException")
    void getLastMonthWeekReportsQuandoForInvocadoENaoHouveremDadosDisponiveisDeveLancarResourceNotFoundExceptionTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getLastMonthWeekReports()
        );

        assertEquals("Dados não encontrados", exception.getMessage());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getLastWeekReport quando invocado deve retornar um report sobre a última semana")
    void getLastWeekReportQuandoInvocadoDeveRetornarUmReportSobreAUltimaSemanaTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(List.of(attestations.get(0)));

        ReportResponseDto result = service.getLastWeekReport();
        assertNotNull(result);
        assertEquals(1, result.qttOrders());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getLastWeekReport quando invocado e não houverem dados deve lançar ResourceNotFoundException")
    void getLastWeekReportQuandoInvocadoENaoHouveremDadosDeveLancarResourceNotFoundExceptionTest() {
        when(repository.findAllByCreatedTimeBetween(any(), any())).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getLastWeekReport()
        );

        assertEquals("Dados não encontrados", exception.getMessage());
        verify(
                repository,
                times(1)
        ).findAllByCreatedTimeBetween(any(), any());
    }

    @Test
    @DisplayName("getAverageDailyRevenue deve retornar a média de faturamento diário corretamente")
    void getAverageDailyRevenueDeveRetornarAMediaDeFaturamentoDiarioCorretamenteTest() {
        BigDecimal result = service.getAverageDailyRevenue(List.of(attestations.get(0), attestations.get(1)), 10);

        assertNotNull(result);
        assertEquals(18.0, result.doubleValue());
    }

    @Test
    @DisplayName("getTotalRevenue deve retornar o faturamento total corretamente")
    void getTotalRevenuedeveRetornarOFaturamentoTotalCorretamenteTest() {
        BigDecimal result = service.getTotalRevenue(List.of(attestations.get(0), attestations.get(1)));

        assertNotNull(result);
        assertEquals(180.0, result.doubleValue());
    }

    @Test
    @DisplayName("getMostOrderedFlavor deve retornar o sabor mais pedido corretamente")
    void getMostOrderedFlavorDeveRetornarOSaborMaisPedidoCorretamenteTest() {
        Flavor result = service.getMostOrderedFlavor(List.of(attestations.get(0), attestations.get(1)));

        assertNotNull(result);
        assertEquals(flavors.get(0), result);
    }

    @Test
    @DisplayName("getMostOrderedDrink deve retornar a bebida mais pedida corretamente")
    void getMostOrderedDrinkDeveRetornarABebidaMaisPedidaCorretamenteTest() {
        Drink result = service.getMostOrderedDrink(List.of(attestations.get(0), attestations.get(1)));

        assertNotNull(result);
        assertEquals(drinks.get(0), result);
    }

}
