package com.devsu.cuentas.application.port.in;

import com.devsu.cuentas.domain.model.Account;

import java.util.List;

public interface AccountUseCase {
    Account create(Account account);
    List<Account> findAll();
    Account findById(String accountNumber);
    Account update(String accountNumber, Account account);
    Account partialUpdate(String accountNumber, Account account);
}
