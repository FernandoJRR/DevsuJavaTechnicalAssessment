package com.devsu.cuentas.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {
    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String transactionType;
    @NotNull(message = "El valor es obligatorio")
    private BigDecimal amount;
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;
}
