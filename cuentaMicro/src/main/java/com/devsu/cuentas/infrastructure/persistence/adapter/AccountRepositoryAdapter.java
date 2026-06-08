package com.devsu.cuentas.infrastructure.persistence.adapter;

import com.devsu.cuentas.application.port.out.AccountRepositoryPort;
import com.devsu.cuentas.domain.model.Account;
import com.devsu.cuentas.infrastructure.persistence.mapper.AccountPersistenceMapper;
import com.devsu.cuentas.infrastructure.persistence.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final AccountJpaRepository jpaRepository;
    private final AccountPersistenceMapper mapper;

    @Override
    public Account save(Account account) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(account)));
    }

    @Override
    public List<Account> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Account> findById(String accountNumber) {
        return jpaRepository.findById(accountNumber).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(String accountNumber) {
        return jpaRepository.existsById(accountNumber);
    }

    @Override
    public List<Account> findByClientId(UUID clientId) {
        return jpaRepository.findByClientId(clientId).stream().map(mapper::toDomain).toList();
    }
}
