package com.devsu.cuentas.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportResponse {

    @JsonProperty("Fecha")
    @JsonFormat(pattern = "M/d/yyyy")
    private LocalDateTime date;

    @JsonProperty("Cliente")
    private String clientName;

    @JsonProperty("Numero Cuenta")
    private String accountNumber;

    @JsonProperty("Tipo")
    private String accountType;

    @JsonProperty("Saldo Inicial")
    private BigDecimal initialBalance;

    @JsonProperty("Estado")
    private boolean active;

    @JsonProperty("Movimiento")
    private BigDecimal transactionAmount;

    @JsonProperty("Saldo Disponible")
    private BigDecimal availableBalance;
}
