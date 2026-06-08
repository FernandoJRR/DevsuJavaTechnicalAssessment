package com.devsu.clientes.application.event;

import com.devsu.clientes.domain.model.Client;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ClientDomainEvent {

    public enum Type { CREATED, UPDATED, DELETED }

    private final Type type;
    private final Client client;
    private final UUID clientId;

    private ClientDomainEvent(Type type, Client client, UUID clientId) {
        this.type = type;
        this.client = client;
        this.clientId = clientId;
    }

    public static ClientDomainEvent created(Client client) {
        return new ClientDomainEvent(Type.CREATED, client, client.getClientId());
    }

    public static ClientDomainEvent updated(Client client) {
        return new ClientDomainEvent(Type.UPDATED, client, client.getClientId());
    }

    public static ClientDomainEvent deleted(UUID clientId) {
        return new ClientDomainEvent(Type.DELETED, null, clientId);
    }
}
