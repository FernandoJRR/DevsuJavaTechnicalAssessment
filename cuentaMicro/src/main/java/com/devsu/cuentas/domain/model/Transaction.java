package com.devsu.cuentas.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private UUID id;
    private LocalDateTime date;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balance;
    private String accountNumber;

    public static Transaction create(String accountNumber, String transactionType,
                                     BigDecimal amount, BigDecimal resultingBalance) {
        Transaction t = new Transaction();
        t.accountNumber = accountNumber;
        t.transactionType = transactionType;
        t.amount = amount;
        t.balance = resultingBalance;
        t.date = LocalDateTime.now();
        return t;
    }
}
