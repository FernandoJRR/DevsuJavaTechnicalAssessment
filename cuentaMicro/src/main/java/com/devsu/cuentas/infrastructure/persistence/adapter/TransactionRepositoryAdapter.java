package com.devsu.cuentas.infrastructure.persistence.adapter;

import com.devsu.cuentas.application.port.out.TransactionRepositoryPort;
import com.devsu.cuentas.domain.model.Transaction;
import com.devsu.cuentas.infrastructure.persistence.mapper.TransactionPersistenceMapper;
import com.devsu.cuentas.infrastructure.persistence.repository.TransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionPersistenceMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(transaction)));
    }

    @Override
    public List<Transaction> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Transaction> findByAccountNumberAndDateBetween(String accountNumber,
                                                                LocalDateTime start,
                                                                LocalDateTime end) {
        return jpaRepository.findByAccountNumberAndDateBetween(accountNumber, start, end)
                .stream().map(mapper::toDomain).toList();
    }
}
