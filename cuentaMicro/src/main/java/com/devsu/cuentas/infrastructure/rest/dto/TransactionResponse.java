package com.devsu.cuentas.infrastructure.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {
    private UUID id;
    private LocalDateTime date;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private String accountNumber;
}
