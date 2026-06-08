package com.devsu.clientes.infrastructure.messaging.dto;

import com.devsu.clientes.infrastructure.messaging.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientEvent {
    private EventType eventType;
    private UUID clientId;
    private String name;
    private boolean active;
}
