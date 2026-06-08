package com.devsu.clientes.application.port.in;

import com.devsu.clientes.domain.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientUseCase {
    Client create(Client client);
    List<Client> findAll();
    Client findById(UUID id);
    Client update(UUID id, Client client);
    Client partialUpdate(UUID id, Client client);
    void delete(UUID id);
}
