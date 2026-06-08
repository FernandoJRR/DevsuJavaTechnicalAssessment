package com.devsu.cuentas.application.service;

import com.devsu.cuentas.application.port.in.AccountUseCase;
import com.devsu.cuentas.application.port.out.AccountRepositoryPort;
import com.devsu.cuentas.domain.exception.AccountNotFoundException;
import com.devsu.cuentas.domain.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements AccountUseCase {

    private final AccountRepositoryPort repositoryPort;

    @Override
    public Account create(Account account) {
        account.setCurrentBalance(account.getInitialBalance());
        return repositoryPort.save(account);
    }

    @Override
    public List<Account> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    public Account findById(String accountNumber) {
        return repositoryPort.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    @Override
    public Account update(String accountNumber, Account account) {
        if (!repositoryPort.existsById(accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        account.setAccountNumber(accountNumber);
        return repositoryPort.save(account);
    }

    @Override
    public Account partialUpdate(String accountNumber, Account account) {
        Account existing = repositoryPort.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
        if (account.getAccountType() != null) existing.setAccountType(account.getAccountType());
        if (account.getInitialBalance() != null) existing.setInitialBalance(account.getInitialBalance());
        if (account.getClientId() != null) existing.setClientId(account.getClientId());
        if (account.getActive() != null) existing.setActive(account.getActive());
        return repositoryPort.save(existing);
    }
}
