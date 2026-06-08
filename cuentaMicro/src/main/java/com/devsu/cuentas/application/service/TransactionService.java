package com.devsu.cuentas.application.service;

import com.devsu.cuentas.application.port.in.TransactionUseCase;
import com.devsu.cuentas.application.port.out.AccountRepositoryPort;
import com.devsu.cuentas.application.port.out.TransactionRepositoryPort;
import com.devsu.cuentas.domain.exception.AccountNotFoundException;
import com.devsu.cuentas.domain.exception.InsufficientBalanceException;
import com.devsu.cuentas.domain.exception.TransactionNotFoundException;
import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    @Transactional
    public Transaction create(Transaction transaction) {
        Account account = accountRepositoryPort.findById(transaction.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(transaction.getAccountNumber()));

        account.applyTransaction(transaction.getAmount());
        accountRepositoryPort.save(account);

        Transaction stamped = Transaction.create(
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                account.getCurrentBalance()
        );
        return transactionRepositoryPort.save(stamped);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepositoryPort.findAll();
    }

    @Override
    public Transaction findById(UUID id) {
        return transactionRepositoryPort.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @Override
    @Transactional
    public Transaction update(UUID id, Transaction transaction) {
        Transaction existing = transactionRepositoryPort.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        Account account = accountRepositoryPort.findById(existing.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(existing.getAccountNumber()));

        // Reverse the old amount, then apply the new one
        BigDecimal reversed = account.getCurrentBalance().subtract(existing.getAmount());
        BigDecimal newBalance = reversed.add(transaction.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException();
        }

        account.setCurrentBalance(newBalance);
        accountRepositoryPort.save(account);

        transaction.setId(id);
        transaction.setBalance(newBalance);
        transaction.setDate(existing.getDate());
        transaction.setAccountNumber(existing.getAccountNumber());
        return transactionRepositoryPort.save(transaction);
    }

    @Override
    @Transactional
    public Transaction partialUpdate(UUID id, Transaction transaction) {
        Transaction existing = transactionRepositoryPort.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        if (transaction.getTransactionType() != null) existing.setTransactionType(transaction.getTransactionType());
        if (transaction.getDate() != null) existing.setDate(transaction.getDate());
        // balance is an invariant computed from the account — not directly patchable
        return transactionRepositoryPort.save(existing);
    }
}
