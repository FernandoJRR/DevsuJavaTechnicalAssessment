package com.devsu.cuentas.application.port.in;

import com.devsu.cuentas.application.service.ReportEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReportUseCase {
    List<ReportEntry> generateReport(LocalDate start, LocalDate end, UUID clientId);
}
