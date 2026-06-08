package com.devsu.clientes.application.service;

import com.devsu.clientes.application.event.ClientDomainEvent;
import com.devsu.clientes.application.port.in.ClientUseCase;
import com.devsu.clientes.application.port.out.ClientRepositoryPort;
import com.devsu.clientes.application.port.out.PasswordHasherPort;
import com.devsu.clientes.domain.exception.ClientNotFoundException;
import com.devsu.clientes.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService implements ClientUseCase {

    private final ClientRepositoryPort repositoryPort;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordHasherPort passwordHasherPort;

    @Override
    @Transactional
    public Client create(Client client) {
        client.setPassword(passwordHasherPort.hash(client.getPassword()));
        Client saved = repositoryPort.save(client);
        eventPublisher.publishEvent(ClientDomainEvent.created(saved));
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return repositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(UUID id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    @Transactional
    public Client update(UUID id, Client client) {
        if (!repositoryPort.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        client.setClientId(id);
        client.setPassword(passwordHasherPort.hash(client.getPassword()));
        Client saved = repositoryPort.save(client);
        eventPublisher.publishEvent(ClientDomainEvent.updated(saved));
        return saved;
    }

    @Override
    @Transactional
    public Client partialUpdate(UUID id, Client client) {
        Client existing = repositoryPort.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        if (client.getName() != null) existing.setName(client.getName());
        if (client.getGender() != null) existing.setGender(client.getGender());
        if (client.getAge() != null) existing.setAge(client.getAge());
        if (client.getIdentification() != null) existing.setIdentification(client.getIdentification());
        if (client.getAddress() != null) existing.setAddress(client.getAddress());
        if (client.getPhone() != null) existing.setPhone(client.getPhone());
        if (client.getPassword() != null) existing.setPassword(passwordHasherPort.hash(client.getPassword()));
        if (client.getActive() != null) existing.setActive(client.getActive());
        Client saved = repositoryPort.save(existing);
        eventPublisher.publishEvent(ClientDomainEvent.updated(saved));
        return saved;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repositoryPort.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        repositoryPort.deleteById(id);
        eventPublisher.publishEvent(ClientDomainEvent.deleted(id));
    }
}
