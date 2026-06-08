package com.devsu.cuentas.infrastructure.persistence.adapter;

import com.devsu.cuentas.application.port.out.ClientInfoRepositoryPort;
import com.devsu.cuentas.domain.model.ClientInfo;
import com.devsu.cuentas.infrastructure.persistence.mapper.ClientInfoPersistenceMapper;
import com.devsu.cuentas.infrastructure.persistence.repository.ClientInfoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientInfoRepositoryAdapter implements ClientInfoRepositoryPort {

    private final ClientInfoJpaRepository jpaRepository;
    private final ClientInfoPersistenceMapper mapper;

    @Override
    public ClientInfo upsert(ClientInfo clientInfo) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(clientInfo)));
    }

    @Override
    public Optional<ClientInfo> findByClientId(UUID clientId) {
        return jpaRepository.findById(clientId).map(mapper::toDomain);
    }

    @Override
    public List<ClientInfo> findByClientId(List<UUID> clientIds) {
        return jpaRepository.findByClientIdIn(clientIds).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void markInactive(UUID clientId) {
        jpaRepository.findById(clientId).ifPresent(entity -> {
            entity.setActive(false);
            jpaRepository.save(entity);
        });
    }
}
