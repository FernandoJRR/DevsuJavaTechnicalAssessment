package com.devsu.clientes.domain.exception;

import java.util.UUID;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(UUID id) {
        super("Cliente no encontrado con id: " + id);
    }
}
