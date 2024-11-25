package com.orderize.backoffice_api.controller;

import com.orderize.backoffice_api.dto.report.ReportResponseDto;
import com.orderize.backoffice_api.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reports", produces = {"application/json"})
@Tag(name = "/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/month")
    @Operation(
            summary = "Report com informações relevantes sobre o último mês"
    )
    public ResponseEntity<ReportResponseDto> getMonthReport() {
        ReportResponseDto report = service.getLastMonthReport();
        return ResponseEntity.status(200).body(report);
    }

    @GetMapping("/month/weeks")
    @Operation(
            summary = "Reports com informações relevantes sobre cada semana do último mês"
    )
    public ResponseEntity<List<ReportResponseDto>> getLastMonthWeeksReports() {
        List<ReportResponseDto> reports = service.getLastMonthWeekReports();
        return ResponseEntity.status(200).body(reports);
    }

    @GetMapping("/week")
    @Operation(
            summary = "Report com informações relevantes sobre a última semana"
    )
    public ResponseEntity<ReportResponseDto> getLastWeekReport() {
        ReportResponseDto report = service.getLastWeekReport();
        return ResponseEntity.status(200).body(report);
    }

}
