package com.devsu.cuentas.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class AccountRequest {
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;
    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String accountType;
    @NotNull(message = "El saldo inicial es obligatorio")
    private BigDecimal initialBalance;
    private Boolean active;
    @NotNull(message = "El id del cliente es obligatorio")
    private UUID clientId;
}
