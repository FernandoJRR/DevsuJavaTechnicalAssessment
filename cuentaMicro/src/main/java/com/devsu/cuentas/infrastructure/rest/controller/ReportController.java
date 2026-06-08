package com.devsu.cuentas.infrastructure.rest.controller;

import com.devsu.cuentas.application.port.in.ReportUseCase;
import com.devsu.cuentas.application.service.ReportEntry;
import com.devsu.cuentas.infrastructure.rest.dto.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final ReportUseCase reportUseCase;

    @GetMapping
    public ResponseEntity<List<ReportResponse>> getReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam UUID clienteId) {

        List<ReportEntry> entries = reportUseCase.generateReport(fechaInicio, fechaFin, clienteId);
        List<ReportResponse> response = entries.stream().map(this::toResponse).toList();
        return ResponseEntity.ok(response);
    }

    private ReportResponse toResponse(ReportEntry entry) {
        ReportResponse r = new ReportResponse();
        r.setDate(entry.getDate());
        r.setClientName(entry.getClientName());
        r.setAccountNumber(entry.getAccountNumber());
        r.setAccountType(entry.getAccountType());
        r.setInitialBalance(entry.getInitialBalance());
        r.setActive(entry.isActive());
        r.setTransactionAmount(entry.getTransactionAmount());
        r.setAvailableBalance(entry.getAvailableBalance());
        return r;
    }
}
