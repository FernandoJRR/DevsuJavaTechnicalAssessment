package com.devsu.cuentas.application.service;

import com.devsu.cuentas.application.port.in.ClientInfoUseCase;
import com.devsu.cuentas.application.port.out.ClientInfoRepositoryPort;
import com.devsu.cuentas.domain.model.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientInfoService implements ClientInfoUseCase {

    private final ClientInfoRepositoryPort clientInfoRepositoryPort;

    @Override
    public void upsert(UUID clientId, String name, boolean active) {
        ClientInfo info = new ClientInfo();
        info.setClientId(clientId);
        info.setName(name);
        info.setActive(active);
        clientInfoRepositoryPort.upsert(info);
    }

    @Override
    public void markInactive(UUID clientId) {
        clientInfoRepositoryPort.markInactive(clientId);
    }
}
