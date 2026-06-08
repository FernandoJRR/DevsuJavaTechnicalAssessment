package com.devsu.cuentas.domain.model;

import com.devsu.cuentas.domain.exception.InsufficientBalanceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private Boolean active = true;
    private UUID clientId;

    public void applyTransaction(BigDecimal amount) {
        BigDecimal newBalance = this.currentBalance.add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException();
        }
        this.currentBalance = newBalance;
    }
}
