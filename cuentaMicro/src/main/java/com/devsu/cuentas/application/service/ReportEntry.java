package com.devsu.cuentas.application.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReportEntry {
    private LocalDateTime date;
    private String clientName;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean active;
    private BigDecimal transactionAmount;
    private BigDecimal availableBalance;
}
