package com.devsu.cuentas.application.port.in;

import java.util.UUID;

public interface ClientInfoUseCase {
    void upsert(UUID clientId, String name, boolean active);
    void markInactive(UUID clientId);
}
