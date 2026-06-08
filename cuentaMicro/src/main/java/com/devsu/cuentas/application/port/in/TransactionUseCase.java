package com.devsu.cuentas.application.port.in;

import com.devsu.cuentas.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionUseCase {
    Transaction create(Transaction transaction);
    List<Transaction> findAll();
    Transaction findById(UUID id);
    Transaction update(UUID id, Transaction transaction);
    Transaction partialUpdate(UUID id, Transaction transaction);
}
