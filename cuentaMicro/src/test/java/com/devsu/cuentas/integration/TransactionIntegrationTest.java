package com.devsu.cuentas.integration;

import com.devsu.cuentas.application.port.in.AccountUseCase;
import com.devsu.cuentas.application.port.in.TransactionUseCase;
import com.devsu.cuentas.application.port.out.AccountRepositoryPort;
import com.devsu.cuentas.domain.exception.InsufficientBalanceException;
import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.domain.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionIntegrationTest {

    @Autowired
    private AccountUseCase accountUseCase;

    @Autowired
    private TransactionUseCase transactionUseCase;

    @Autowired
    private AccountRepositoryPort accountRepositoryPort;

    @Test
    void deposit_updatesAccountBalance() {
        // Given
        Account account = new Account();
        account.setAccountNumber("ACC-INT-001");
        account.setAccountType("Ahorros");
        account.setInitialBalance(new BigDecimal("1000.00"));
        account.setActive(true);
        account.setClientId(UUID.randomUUID());
        accountUseCase.create(account);

        Transaction tx = new Transaction();
        tx.setAccountNumber("ACC-INT-001");
        tx.setTransactionType("Crédito");
        tx.setAmount(new BigDecimal("500.00"));

        // When
        Transaction result = transactionUseCase.create(tx);

        // Then
        assertEquals(new BigDecimal("1500.00"), result.getBalance());
        Account updated = accountRepositoryPort.findById("ACC-INT-001").orElseThrow();
        assertEquals(new BigDecimal("1500.00"), updated.getCurrentBalance());
    }

    @Test
    void withdrawal_exceedingBalance_fails() {
        // Given
        Account account = new Account();
        account.setAccountNumber("ACC-INT-002");
        account.setAccountType("Corriente");
        account.setInitialBalance(new BigDecimal("100.00"));
        account.setActive(true);
        account.setClientId(UUID.randomUUID());
        accountUseCase.create(account);

        Transaction tx = new Transaction();
        tx.setAccountNumber("ACC-INT-002");
        tx.setTransactionType("Débito");
        tx.setAmount(new BigDecimal("-200.00"));

        // When
        // Then
        assertThrows(InsufficientBalanceException.class, () -> transactionUseCase.create(tx));
    }
}
