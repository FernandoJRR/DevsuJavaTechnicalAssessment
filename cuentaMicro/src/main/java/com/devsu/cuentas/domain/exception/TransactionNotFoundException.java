package com.devsu.cuentas.domain.exception;

import java.util.UUID;

public class TransactionNotFoundException extends ResourceNotFoundException {
    public TransactionNotFoundException(UUID id) {
        super("Movimiento no encontrado con id: " + id);
    }
}
