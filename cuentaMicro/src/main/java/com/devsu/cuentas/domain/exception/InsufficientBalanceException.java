package com.devsu.cuentas.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo no disponible");
    }
}
