package com.devsu.cuentas.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    private String accountNumber;

    private String accountType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal initialBalance;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal currentBalance;

    private boolean active;

    private UUID clientId;
}
