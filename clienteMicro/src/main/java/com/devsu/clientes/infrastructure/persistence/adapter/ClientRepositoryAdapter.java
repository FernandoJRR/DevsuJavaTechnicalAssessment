package com.devsu.clientes.infrastructure.persistence.adapter;

import com.devsu.clientes.application.port.out.ClientRepositoryPort;
import com.devsu.clientes.domain.model.Client;
import com.devsu.clientes.infrastructure.persistence.mapper.ClientPersistenceMapper;
import com.devsu.clientes.infrastructure.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final ClientJpaRepository jpaRepository;
    private final ClientPersistenceMapper mapper;

    @Override
    public Client save(Client client) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(client)));
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
