package com.devsu.cuentas.domain.exception;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String accountNumber) {
        super("Cuenta no encontrada con número: " + accountNumber);
    }
}
